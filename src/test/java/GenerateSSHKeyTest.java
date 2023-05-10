import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.CookieLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerateSSHKeyTest {
    private static final String USER_DROPDOWN_XPATH = "/html/body/header/div/div/div[3]/ul/li[6]/a";
    private static final String PREFERENCES_BUTTON_XPATH = "/html/body/header/div/div/div[3]/ul/li[6]/div/ul/li[5]/a";
    private static final String SSHKEYS_BUTTON_XPATH = "/html/body/div[3]/aside/div/ul/li[11]/a/span[2]";
    private static final String SSH_KEY_INPUT_XPATH = "//*[@id=\"key_key\"]";
    private static final String SSH_KEY_TITLE_INPUT_XPATH = "//*[@id=\"key_title\"]";
    private static final String SSH_KEY_ADD_XPATH = "//*[@id=\"new_key\"]/div[6]/button";
    private static final String CARD_WITH_SSH_KEY = "//*[@id=\"content-body\"]/div[3]/div[1]/div";
    String userDataDir = "/Users/macbook/Library/Application\\ Support/Google/Chrome";
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--profile-directory=Default");
        options.addArguments("--user-data-dir=" + userDataDir);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Generate SSH key test")
    public void testGenerateSSHkey() throws IOException, InterruptedException {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_DROPDOWN_XPATH)));
        driver.findElement(By.xpath(USER_DROPDOWN_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PREFERENCES_BUTTON_XPATH)));
        driver.findElement(By.xpath(PREFERENCES_BUTTON_XPATH)).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SSHKEYS_BUTTON_XPATH))).click();

        String keyName = "test_ssh_key";
        ProcessBuilder processBuilder = new ProcessBuilder("ssh-keygen", "-t", "ed25519", "-f", keyName, "-N", "");
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process process = processBuilder.start();
        process.waitFor();

        String publicKeyPath = keyName + ".pub";
        String publicKeyContent = Files.readString(Paths.get(publicKeyPath));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SSH_KEY_INPUT_XPATH)));
        driver.findElement(By.xpath(SSH_KEY_INPUT_XPATH)).sendKeys(publicKeyContent);

        driver.findElement(By.xpath(SSH_KEY_TITLE_INPUT_XPATH)).sendKeys("Test SSH Key");

        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SSH_KEY_ADD_XPATH)));
        addButton.click();

        Files.deleteIfExists(Paths.get(keyName));
        Files.deleteIfExists(Paths.get(publicKeyPath));

        assertNotNull(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CARD_WITH_SSH_KEY))));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
