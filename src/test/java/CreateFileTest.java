import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.CookieLoader;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateFileTest {

    private WebDriver driver;
    private WebDriverWait wait;

    String userDataDir = "/Users/macbook/Library/Application\\ Support/Google/Chrome";

    private static final String PROJECT_NAME_XPATH = "//*[@id=\"content-body\"]/div[5]/ul/li/div[2]/div/div[1]/h2/a";
    private static final String PLUS_BUTTON_XPATH = "/html/body/div[3]/div/div[3]/main/div[4]/div/div[2]/div[1]/nav/ol/li[2]/div/button";
    private static final String NEW_FILE_XPATH = "/html/body/div[3]/div/div[3]/main/div[4]/div/div[2]/div[1]/nav/ol/li[2]/div/ul/div/div/li[2]/a";
    private static final String FILENAME_INPUT_XPATH = "//*[@id=\"file_name\"]";
    private static final String COMMIT_MESSAGE_INPUT_XPATH = "/html/body/div[3]/div/div[3]/main/div[2]/form/div[2]/div/textarea";
    private static final String COMMIT_BUTTON_XPATH = "//*[@id=\"commit-changes\"]";
    private static final String FILE_CREATED_MESSAGE_XPATH = "//*[@id=\"content-body\"]/div[1]/div/div/div";

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
    @DisplayName("Create File test")
    public void testCreateFile() {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PROJECT_NAME_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PLUS_BUTTON_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(NEW_FILE_XPATH))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILENAME_INPUT_XPATH))).sendKeys("testfile.txt");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COMMIT_MESSAGE_INPUT_XPATH))).sendKeys("Add testfile.txt");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(COMMIT_BUTTON_XPATH)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(COMMIT_BUTTON_XPATH))).click();

        String fileCreatedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILE_CREATED_MESSAGE_XPATH))).getText();
        assertEquals("The file has been successfully created.", fileCreatedMessage);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
