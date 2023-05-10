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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectHistoryTest {
    private static final String PROJECT_NAME_XPATH = "//*[@id=\"content-body\"]/div[5]/ul/li/div[2]/div/div[1]/h2/a";
    private static final String FILE_HISTORY_XPATH = "//*[@id=\"tree-holder\"]/div[2]/div[2]/div[1]/a[1]";
    private static final String COMMIT_LIST_XPATH = "//*[@id=\"commits-list\"]/li[2]";
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
    @DisplayName("See project history test")
    public void testSeeProjectHistory() {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PROJECT_NAME_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(FILE_HISTORY_XPATH))).click();

        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COMMIT_LIST_XPATH))).isDisplayed());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
