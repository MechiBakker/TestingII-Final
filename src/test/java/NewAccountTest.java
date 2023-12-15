import Reports.Base.BasePage;
import Reports.ExtentFactory;
import Reports.Profile.NewAccountPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class NewAccountTest extends BasePage {

    private NewAccountPage openNewAccountPage;
    private WebDriver driver;
    static ExtentSparkReporter spark = new ExtentSparkReporter("target/SparkFrontEnd2.html");
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
        openNewAccountPage = new NewAccountPage(driver, null);
        openNewAccountPage.link("https://parabank.parasoft.com/parabank/index.htm");
    }

    @Test
    @Tag("Front-End")
    public void successNewAccount() throws InterruptedException {
        test = extent.createTest("Open new account");
        test.log(Status.INFO, "Starting test");

        openNewAccountPage.login("mechibakker", "A2C4E6");
        test.log(Status.INFO, "User log in test");

        openNewAccountPage.clickOpenNewAccountMenu();

        test.log(Status.PASS, "Open page to create a new user account");

        openNewAccountPage.selectAccountType();

        test.log(Status.PASS, "Selecting type account <SAVINGS>.");

        openNewAccountPage.clickEnterBtn();

        assertTrue(openNewAccountPage.result().contains("Congratulations, your account is now open."));

        test.log(Status.PASS, "Verifying new account \"Congratulations, your account is now open.\" visualisation");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
}
