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

import java.io.IOException;
import java.time.Duration;

public class CreateRepositoryTest {
    private WebDriver driver;
    private WebDriverWait wait;

    String userDataDir = "/Users/macbook/Library/Application\\ Support/Google/Chrome";

    private static final String CREATE_REPOSITORY_XPATH = "//*[@id=\"content-body\"]/div[2]/div[2]/a[1]";
    private static final String BLANK_PROJECT_XPATH = "//*[@id=\"content-body\"]/div[2]/div[2]/div[2]/div[1]/div[1]/a";
    private static final String PROJECT_NAME_XPATH = "//*[@id=\"project_name\"]";
    private static final String PUBLIC_PROJECT_RADIO_BUTTON_XPATH = "//*[@id=\"new_project\"]/div[6]/div/div[2]/label";
    private static final String CREATE_PROJECT_BUTTON_XPATH = "//*[@id=\"new_project\"]/button";
    private static final String PROJECT_CREATED_MESSAGE_XPATH = "//*[@id=\"content-body\"]/div[1]/div/div/div";

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
    @DisplayName("Create project test")
    public void testCreateProject() throws IOException, InterruptedException {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CREATE_REPOSITORY_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(BLANK_PROJECT_XPATH))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PROJECT_NAME_XPATH))).sendKeys("Test Project");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PUBLIC_PROJECT_RADIO_BUTTON_XPATH))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CREATE_PROJECT_BUTTON_XPATH))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PROJECT_CREATED_MESSAGE_XPATH)));
        assert driver.findElement(By.xpath(PROJECT_CREATED_MESSAGE_XPATH)).getText().equals("Project was successfully created.");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
