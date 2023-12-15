import Reports.Base.BasePage;
import Reports.ExtentFactory;
import Reports.Profile.AccountsOverviewPage;
import Reports.Base.BasePage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import Reports.ExtentFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import Reports.Profile.AccountsOverviewPage;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class AccountsOverviewTest extends BasePage {

    private AccountsOverviewPage accountsOverviewPage;
    private WebDriver driver;
    static ExtentSparkReporter spark = new ExtentSparkReporter("target/SparkFrontEnd4.html");
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
        accountsOverviewPage = new AccountsOverviewPage(driver, null);
        accountsOverviewPage.link("https://parabank.parasoft.com/parabank/index.htm");
    }


    @Test()
    @Tag("Front-End")
    public void viewingAccountDetails() throws InterruptedException {
        test = extent.createTest("Count activity visualisation");
        test.log(Status.INFO, "Starting test");

        accountsOverviewPage.login("mechibakker", "A2C4E6");
        test.log(Status.INFO, "Starting user test");

        accountsOverviewPage.clickAccountOverview();

        test.log(Status.PASS, "Open page for user account visualisation");

        assertTrue(accountsOverviewPage.checkTitle().equals("*Balance includes deposits that may be subject to holds"));
        test.log(Status.PASS, "Checking text \"*El saldo incluye depósitos que pueden estar sujetos a retenciones\" visualisation");

        accountsOverviewPage.selectAccount();
        assertTrue(accountsOverviewPage.checkTitleAccountDetails().equals("Account Details"));
        test.log(Status.PASS, "Checking account details and title \"Account Details\" visualisation");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
}
