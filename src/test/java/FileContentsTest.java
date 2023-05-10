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

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileContentsTest {
    private static final String PROJECT_NAME_XPATH = "//*[@id=\"content-body\"]/div[5]/ul/li/div[2]/div/div[1]/h2/a";
    private static final String README_XPATH = "//a[contains(@class, 'tree-item-link') and contains(., 'README.md')]\n";
    private static final String FILEHOLDER_PATH = "//*[@id=\"fileHolder\"]";
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
    @DisplayName("See file contents test")
    public void testSeeFileContents() {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PROJECT_NAME_XPATH))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(README_XPATH)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(README_XPATH))).click();

        assertNotNull(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILEHOLDER_PATH))));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
