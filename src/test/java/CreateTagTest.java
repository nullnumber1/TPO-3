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

public class CreateTagTest {
    private WebDriver driver;
    private WebDriverWait wait;

    String userDataDir = "/Users/macbook/Library/Application\\ Support/Google/Chrome";

    private static final String BLANK_PROJECT_XPATH = "//*[@id=\"content-body\"]/div[2]/div[2]/div[2]/div[1]/div[1]/a";
    private static final String PROJECT_NAME_XPATH = "//*[@id=\"content-body\"]/div[5]/ul/li/div[2]/div/div[1]/h2/a";
    private static final String PLUS_BUTTON_XPATH = "/html/body/div[3]/div/div[3]/main/div[4]/div/div[2]/div[1]/nav/ol/li[2]/div/button";
    private static final String NEW_TAG_XPATH = "/html/body/div[3]/div/div[3]/main/div[4]/div/div[2]/div[1]/nav/ol/li[2]/div/ul/div/div/li[8]/a";
    private static final String TAG_NAME_INPUT_XPATH = "//*[@id=\"tag_name\"]";
    private static final String TAG_MESSAGE_INPUT_XPATH = "//*[@id=\"message\"]";
    private static final String CREATE_TAG_BUTTON_XPATH = "//*[@id=\"new-tag-form\"]/div[4]/button";
    private static final String CREATED_TAG_MESSAGE_XPATH = "//*[@id=\"content-body\"]/div[2]/pre";

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
    @DisplayName("Create tag test")
    public void testCreateTag() {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PROJECT_NAME_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PLUS_BUTTON_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(NEW_TAG_XPATH))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TAG_NAME_INPUT_XPATH))).sendKeys("v1.0");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TAG_MESSAGE_INPUT_XPATH))).sendKeys("Initial release");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CREATE_TAG_BUTTON_XPATH))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CREATED_TAG_MESSAGE_XPATH)));

        assertEquals("Initial release", driver.findElement(By.xpath(CREATED_TAG_MESSAGE_XPATH)).getText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
