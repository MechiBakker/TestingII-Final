import Reports.Base.BasePage;
import Reports.ExtentFactory;
import Reports.Profile.TransferFundsPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TransferFundsTest extends BasePage {

    private TransferFundsPage transferFundsPage;
    private WebDriver driver;
    static ExtentSparkReporter spark = new ExtentSparkReporter("target/SparkFrontEnd3.html");
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
        transferFundsPage = new TransferFundsPage(driver, null);
        transferFundsPage.link("https://parabank.parasoft.com/parabank/index.htm");
    }

    @Test
    @Tag("Front-End")
    public void successTransfer() throws InterruptedException {
        test = extent.createTest("Funds transfer");
        test.log(Status.INFO, "Starting test");

        transferFundsPage.login("mechibakker", "A2C4E6");
        test.log(Status.INFO, "User log in test");

        transferFundsPage.clickTransferFundsMenu();

        test.log(Status.PASS, "Redirect to transfer");

        assertTrue(transferFundsPage.checkTitle().equals("Transfer Funds"));
        test.log(Status.PASS, "Checking transfer page \"Transfer funds\" visualisation");

        transferFundsPage.completeForm("100");
        transferFundsPage.clickEnterBtn();

        assertTrue(transferFundsPage.result().equals("Transfer Complete!"));

        test.log(Status.PASS, "Filling and checking form \"¡Transfer completed!\" visualisation");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
}
