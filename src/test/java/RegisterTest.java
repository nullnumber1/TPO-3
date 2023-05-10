import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Scanner;

public class RegisterTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Scanner scanner;

    private static final String SIGN_IN_BUTTON_XPATH = "//*[@id=\"be-navigation-desktop\"]/div/div/div[2]/div/a";
    private static final String REGISTER_LINK_XPATH = "//*[@id=\"signin-container\"]/p[2]/a";
    private static final String FORM_CONTAINER_XPATH = "/html/body/div[1]/div[2]/div/div[3]/div/div";
    private static final String FIRST_NAME_INPUT_XPATH = "//*[@id=\"new_user_first_name\"]";
    private static final String LAST_NAME_INPUT_XPATH = "//*[@id=\"new_user_last_name\"]";
    private static final String USERNAME_INPUT_XPATH = "//*[@id=\"new_user_username\"]";
    private static final String USERNAME_IS_AVAILABLE_XPATH = "//*[@id=\"new_new_user\"]/div[4]/p[4]";
    private static final String EMAIL_INPUT_XPATH = "//*[@id=\"new_user_email\"]";
    private static final String PASSWORD_INPUT_XPATH = "//*[@id=\"new_user_password\"]";
    private static final String REGISTER_BUTTON_XPATH = "//*[@id=\"new_new_user\"]/div[9]/button";
    private static final String VERIFCATION_CODE_INPUT_XPATH = "//*[@id=\"__BVID__10\"]";
    private static final String GITLAB_GREETING_XPATH = "//*[@id=\"content-body\"]/div[2]/div/div/h2";

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        scanner = new Scanner(System.in);
    }

    @Test
    @DisplayName("Register test")
    public void testRegister() {
        driver.manage().window().maximize();
        driver.navigate().to("https://about.gitlab.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SIGN_IN_BUTTON_XPATH)));
        driver.findElement(By.xpath(SIGN_IN_BUTTON_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(REGISTER_LINK_XPATH)));
        driver.findElement(By.xpath(REGISTER_LINK_XPATH)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FORM_CONTAINER_XPATH)));
        driver.findElement(By.xpath(FIRST_NAME_INPUT_XPATH)).sendKeys("test_name");
        driver.findElement(By.xpath(LAST_NAME_INPUT_XPATH)).sendKeys("test_last_name");
        driver.findElement(By.xpath(USERNAME_INPUT_XPATH)).sendKeys("test_username");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USERNAME_IS_AVAILABLE_XPATH)));

        driver.findElement(By.xpath(EMAIL_INPUT_XPATH)).sendKeys("test_email");
        driver.findElement(By.xpath(PASSWORD_INPUT_XPATH)).sendKeys("test_password");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(REGISTER_BUTTON_XPATH)));
        driver.findElement(By.xpath(REGISTER_BUTTON_XPATH)).click();

        System.out.println("Enter verification code: ");
        String verificationCode = scanner.nextLine();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(VERIFCATION_CODE_INPUT_XPATH)));
        driver.findElement(By.xpath(VERIFCATION_CODE_INPUT_XPATH)).sendKeys(verificationCode);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(GITLAB_GREETING_XPATH)));
        assert driver.findElement(By.xpath(GITLAB_GREETING_XPATH)).isDisplayed();
    }
}
