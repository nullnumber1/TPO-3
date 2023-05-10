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

public class NewBranchCreationTest {
    private WebDriver driver;
    private WebDriverWait wait;

    String userDataDir = "/Users/macbook/Library/Application\\ Support/Google/Chrome";

    private static final String PROJECT_NAME_XPATH = "//*[@id=\"content-body\"]/div[5]/ul/li/div[2]/div/div[1]/h2/a";
    private static final String PLUS_BUTTON_XPATH = "/html/body/div[3]/div/div[3]/main/div[4]/div/div[2]/div[1]/nav/ol/li[2]/div/button";
    private static final String NEW_BRANCH_XPATH = "//*[@id=\"__BVID__69\"]/ul/div/div/li[7]";
    private static final String BRANCH_NAME_INPUT_XPATH = "//*[@id=\"branch_name\"]";
    private static final String CREATE_BRANCH_BUTTON_XPATH = "//*[@id=\"new-branch-form\"]/button";
    private static final String BRANCH_CREATED_MESSAGE_XPATH = "//*[@id=\"content-body\"]/div[2]/div/div[1]";

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
    @DisplayName("Create branch test")
    public void testCreateBranch() {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PROJECT_NAME_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PLUS_BUTTON_XPATH))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NEW_BRANCH_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(BRANCH_NAME_INPUT_XPATH))).sendKeys("test-branch");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CREATE_BRANCH_BUTTON_XPATH))).click();

        String branchCreatedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BRANCH_CREATED_MESSAGE_XPATH))).getText();

        assertEquals("You pushed to\n" +
                "test-branch\n" +
                "just now", branchCreatedMessage);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
