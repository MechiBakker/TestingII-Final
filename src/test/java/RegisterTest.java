import Reports.Base.BasePage;
import Reports.ExtentFactory;
import Reports.Profile.RegisterPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterTest extends BasePage {


    private RegisterPage registerPage;
    private WebDriver driver;
    static ExtentSparkReporter spark = new ExtentSparkReporter("target/REPORTES.html");
    static ExtentReports extent;
    ExtentTest test;

    @BeforeAll
    static void report() {
        extent = ExtentFactory.getInstance("Selenium version", "4.0");
        extent.attachReporter(spark);
    }

    @BeforeEach
    public void setUp() throws Exception {
        driver = super.chromeDriverConnection();
        registerPage = new RegisterPage(driver, null);
        registerPage.link("https://parabank.parasoft.com/parabank/index.htm");
    }

    @Test
    @Tag("Front-End")
    public void successfulRegistration() throws InterruptedException {
        test = extent.createTest("Registration");
        test.log(Status.INFO, "Starting Test");

        registerPage.clickRegistrationMenu();

        test.log(Status.PASS, "Open registration page");

        registerPage.completeForm("Mercedes",
                "Bakker",
                "Diagonal 76 390",
                "La Plata",
                "Buenos Aires",
                "1900",
                "2213091506",
                "0303456",
                "mechibakker",
                "A2C4E6",
                "A2C4E6");

        test.log(Status.PASS, "Complete form");

        registerPage.clickEnterBtn();

        assertTrue(registerPage.result().equals("Your account has been created successfully. You are now logged in!"));

        test.log(Status.PASS, "Verify user");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        extent.flush();
    }

}
