import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.Scanner;

public class SignInTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Scanner scanner;

    private File uBlockOrigin = new File("src/test/resources/1.49.2_0.crx");

    private static final String SIGN_IN_BUTTON_XPATH = "//*[@id=\"be-navigation-desktop\"]/div/div/div[2]/div/a";
    private static final String FORM_CONTAINER_XPATH = "//*[@id=\"signin-container\"]";
    private static final String USERNAME_INPUT_XPATH = "//*[@id=\"user_login\"]";
    private static final String PASSWORD_INPUT_XPATH = "//*[@id=\"user_password\"]";
    private static final String SIGN_IN_BUTTON_2_XPATH = "//*[@id=\"new_user\"]/div[4]/button";
    private static final String GREETING_XPATH = "//*[@id=\"content-body\"]/div[2]/div[1]/h2";

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-blink-features=AutomationControlled");
//        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
//        options.setExperimentalOption("useAutomationExtension", false);
//        options.addExtensions(uBlockOrigin);
        driver = new ChromeDriver(options);

        driver.manage().window().setSize(new Dimension(1366, 768));
        ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        scanner = new Scanner(System.in);
    }

    @Test
    @DisplayName("Login test")
    public void testLogin() {
        driver.navigate().to("https://about.gitlab.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SIGN_IN_BUTTON_XPATH)));
        driver.findElement(By.xpath(SIGN_IN_BUTTON_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FORM_CONTAINER_XPATH)));

        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        driver.findElement(By.xpath(USERNAME_INPUT_XPATH)).sendKeys(username);
        driver.findElement(By.xpath(PASSWORD_INPUT_XPATH)).sendKeys(password);

        driver.findElement(By.xpath(SIGN_IN_BUTTON_2_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(GREETING_XPATH)));
        assert driver.findElement(By.xpath(GREETING_XPATH)).isDisplayed();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
