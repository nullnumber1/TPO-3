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

public class EditProfileSettingTest {
    private WebDriver driver;
    private WebDriverWait wait;

    String userDataDir = "/Users/macbook/Library/Application\\ Support/Google/Chrome";

    private static final String USER_DROPDOWN_XPATH = "/html/body/header/div/div/div[3]/ul/li[6]/a";
    private static final String EDIT_PROFILE_BUTTON_XPATH = "/html/body/header/div/div/div[3]/ul/li[6]/div/ul/li[4]/a";
    private static final String EDIT_FIELD_DIV_XPATH = "/html/body/div[3]/div/div[3]/main/form";
    private static final String EDIT_NAME_INPUT_XPATH = "//*[@id=\"user_name\"]";
    private static final String PROFILE_NAME_XPATH = "/html/body/header/div/div/div[3]/ul/li[6]/div/ul/li[1]/a/div";
    private static final String SAVE_CHANGES_BUTTON_XPATH = "/html/body/div[3]/div/div[3]/main/form/div[5]/div/button";
    private static final String PROFILE_UPDATED_MESSAGE_XPATH = "//*[@id=\"content-body\"]/div[1]/div/div/div";

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
    @DisplayName("Edit profile setting test")
    public void testEditProfileSetting() {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_DROPDOWN_XPATH)));
        driver.findElement(By.xpath(USER_DROPDOWN_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EDIT_PROFILE_BUTTON_XPATH)));
        driver.findElement(By.xpath(EDIT_PROFILE_BUTTON_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EDIT_FIELD_DIV_XPATH)));

        driver.findElement(By.xpath(EDIT_NAME_INPUT_XPATH)).clear();
        driver.findElement(By.xpath(EDIT_NAME_INPUT_XPATH)).sendKeys("Romanov Artyom");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SAVE_CHANGES_BUTTON_XPATH)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(SAVE_CHANGES_BUTTON_XPATH)));
        driver.findElement(By.xpath(SAVE_CHANGES_BUTTON_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PROFILE_UPDATED_MESSAGE_XPATH)));

        driver.navigate().refresh();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_DROPDOWN_XPATH)));
        driver.findElement(By.xpath(USER_DROPDOWN_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PROFILE_NAME_XPATH)));
        assertEquals("Romanov Artyom", driver.findElement(By.xpath(PROFILE_NAME_XPATH)).getText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
