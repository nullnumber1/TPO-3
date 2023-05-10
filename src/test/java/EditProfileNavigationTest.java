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

public class EditProfileNavigationTest {
    private static final String USER_DROPDOWN_XPATH = "/html/body/header/div/div/div[3]/ul/li[6]/a";
    private static final String EDIT_PROFILE_BUTTON_XPATH = "/html/body/header/div/div/div[3]/ul/li[6]/div/ul/li[4]/a";
    private static final String EDIT_FIELD_DIV_XPATH = "/html/body/div[3]/div/div[3]/main/form";
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
    @DisplayName("Edit profile test")
    public void testEditProfile() {
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
        assert driver.findElement(By.xpath(EDIT_FIELD_DIV_XPATH)).isDisplayed();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
