package ku.ac.th.billbroweb;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private static WebDriverWait wait;

    @FindBy(id = "username")
    private WebElement idField;

    @FindBy(id = "password")
    private WebElement pinField;

    @FindBy(id = "submitbtn")
    private WebElement submitButton;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 1000);
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/login");
        PageFactory.initElements(driver, this);
    }

    @AfterEach
    public void afterEach() throws InterruptedException {
        Thread.sleep(3000);
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @Test
    void testLoginCorrectIdPin() {

        idField.sendKeys("nz3672");
        pinField.sendKeys("1234");
        submitButton.click();

        assertTrue(driver.getCurrentUrl().endsWith("home"));
    }

    @Test
    void testLoginIncorrectIdPin() {
        idField.sendKeys("nz3672");
        pinField.sendKeys("12345");
        submitButton.click();

        assertTrue(driver.getCurrentUrl().endsWith("login?error"));
    }

}
