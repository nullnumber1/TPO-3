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

public class SignOutTest {
    private WebDriver driver;
    private WebDriverWait wait;

    String userDataDir = "/Users/macbook/Library/Application\\ Support/Google/Chrome";

    private static final String USER_DROPDOWN_XPATH = "/html/body/header/div/div/div[3]/ul/li[6]/a";
    private static final String SIGN_OUT_BUTTON_XPATH = "/html/body/header/div/div/div[3]/ul/li[6]/div/ul/li[16]/a";
    private static final String FORM_CONTAINER_XPATH = "//*[@id=\"signin-container\"]";

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
    @DisplayName("Sign out test")
    public void testSignOut() {
        driver.manage().window().maximize();
        driver.navigate().to("https://gitlab.com");
        CookieLoader.loadCookiesFromFile(driver, "src/test/resources/cookies/cookies.txt");
        driver.navigate().refresh();

        driver.navigate().to("https://gitlab.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_DROPDOWN_XPATH)));
        driver.findElement(By.xpath(USER_DROPDOWN_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SIGN_OUT_BUTTON_XPATH)));
        driver.findElement(By.xpath(SIGN_OUT_BUTTON_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FORM_CONTAINER_XPATH)));
        assert driver.findElement(By.xpath(FORM_CONTAINER_XPATH)).isDisplayed();
    }
}
