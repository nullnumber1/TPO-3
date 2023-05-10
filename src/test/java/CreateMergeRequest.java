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

public class CreateMergeRequest {
    private static final String CREATE_MR_BUTTON_XPATH = "//*[@id=\"content-body\"]/div[2]/div/div[2]/a";
    private static final String MR_TITLE_XPATH = "//*[@id=\"merge_request_title\"]";
    private static final String MR_DESCRIPTION_XPATH = "//*[@id=\"merge_request_description\"]";
    private static final String ASIGNEE_XPATH = "//*[@id=\"new_merge_request\"]/div[5]/div/div[1]/div/a";
    private static final String CREATE_MR_BUTTON = "//*[@id=\"new_merge_request\"]/div[7]/button";
    private static final String MR_OVERVIEW_XPATH = "//*[@id=\"content-body\"]/div[2]/div[2]/div[1]";
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
    @DisplayName("Create MR test")
    public void testCreateMR() {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CREATE_MR_BUTTON_XPATH))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MR_TITLE_XPATH))).sendKeys("Test MR");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MR_DESCRIPTION_XPATH))).sendKeys("Test MR description");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ASIGNEE_XPATH))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CREATE_MR_BUTTON))).click();

        assertNotNull(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MR_OVERVIEW_XPATH))));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
