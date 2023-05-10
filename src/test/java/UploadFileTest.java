import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.CookieLoader;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UploadFileTest {
    private static final String PROJECT_NAME_XPATH = "//*[@id=\"content-body\"]/div[6]/ul/li/div[2]/div/div[1]/h2/a";
    private static final String BRANCH_SELECTOR_XPATH = "//*[@id=\"tree-holder\"]/div[2]/div[1]/div/div/div";
    private static final String TARGET_BRANCH_XPATH = "//li[contains(@class, 'gl-new-dropdown-item') and contains(., 'test-branch')]";
    private static final String PLUS_BUTTON_XPATH = "/html/body/div[3]/div/div[3]/main/div[2]/div[2]/div[1]/nav/ol/li[2]/div/button";
    private static final String UPLOAD_FILE_XPATH = "//a[contains(@class, 'dropdown-item') and contains(., 'Upload file')]";
    private static final String UPLOAD_FILE_LINK_XPATH = "/html/body/div[7]/div[1]/div/div/div/div[1]/button/div/p/a";
    ;
    private static final String COMMIT_MESSAGE_INPUT_XPATH = "/html/body/div[7]/div[1]/div/div/div/div[2]/div/textarea";
    private static final String UPLOAD_BUTTON_XPATH = "/html/body/div[7]/div[1]/div/div/footer/button[2]";
    private static final String FILE_CREATED_MESSAGE_XPATH = "//*[@id=\"content-body\"]/div[1]/div/div/div";
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
    @DisplayName("Upload File test")
    public void testUploadFile() {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PROJECT_NAME_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(BRANCH_SELECTOR_XPATH))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TARGET_BRANCH_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PLUS_BUTTON_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(UPLOAD_FILE_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(UPLOAD_FILE_LINK_XPATH))).click();

        // Waiting here for user to select the file

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COMMIT_MESSAGE_INPUT_XPATH))).sendKeys("Uploading a file into test-branch");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(UPLOAD_BUTTON_XPATH))).click();

        String fileCreatedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILE_CREATED_MESSAGE_XPATH))).getText();
        assertEquals("The file has been successfully created.", fileCreatedMessage);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
