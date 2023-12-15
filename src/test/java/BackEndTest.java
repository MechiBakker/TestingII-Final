import Reports.ExtentFactory;
import Utils.Account;
import Utils.Customer;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import jdk.jfr.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Tag;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


public class TestBackEnd {
    private String baseURI = "https://parabank.parasoft.com/parabank/services/bank";
    private String username = "mechibakker";
    private String password = "A2C4E6";
    private static ExtentSparkReporter spark = new ExtentSparkReporter("target/REPORTES.html");
    private static ExtentReports extent;
    private ExtentTest test;

    @BeforeTest
    static void report() {
        extent = ExtentFactory.getInstance("Rest Assured", "4.3.3");
        extent.attachReporter(spark);
    }


    @Test(priority = 1)
    @Tag("Back-End")
    public void testGetRegisterPage() {
        test = extent.createTest("Registration");
        test.log(Status.INFO, "Starting test");

        given()
                .when()
                .get("https://parabank.parasoft.com/parabank/register.htm")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .log()
                .status();
        test.log(Status.PASS, "Se verifica el status 200 en la respuesta.");
    }

    @Test(priority = 2)
    @Tag("Back-End")
    public void testLogin() {
        test = extent.createTest("Proceso de ligin.");
        test.log(Status.INFO, "Inicia el test...");
        Customer customer =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(baseURI + "/login/" + username + "/" + password)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().as(Customer.class);

        System.out.println(customer);
        test.log(Status.PASS, "User successfully logged");

    }

    @Test(priority = 3)
    @Tag("Back-End")
    public void testViewCustomerAccounts() {
        test = extent.createTest("Getting user accounts");
        test.log(Status.INFO, "Starting test");

        Customer customer =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(baseURI + "/login/" + username + "/" + password)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().as(Customer.class);

        System.out.println(customer);
        test.log(Status.PASS, "User successfully logged by ID: " + customer.getId());
        test.log(Status.INFO, "Getting user accounts info");

        Response res =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .auth().basic(username, password)
                        .get(baseURI + "/customers/" + customer.getId() + "/accounts")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().response();

        XmlPath xmlObj = new XmlPath(res.asString());
        List<String> accounts = xmlObj.getList("accounts.account");

        test.log(Status.PASS, "Accounts details: " + accounts);

        System.out.println(accounts);

    }


    @Test(priority = 4)
    @Tag("Back-End")
    public void testCreateNewAccount() {
        test = extent.createTest("Open new account");
        test.log(Status.INFO, "Starting test");

        Customer customer =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(baseURI + "/login/" + username + "/" + password)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().as(Customer.class);

        System.out.println(customer);
        test.log(Status.PASS, "User successfully logged by ID: " + customer.getId());

        Response res =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .auth().basic(username, password)
                        .get(baseURI + "/customers/" + customer.getId() + "/accounts")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().response();

        XmlPath xmlObj = new XmlPath(res.asString());
        List<String> accountsId = xmlObj.getList("accounts.account.id");


        System.out.println(accountsId);

        String myCustomerId = customer.getId();
        String firstAccountId = xmlObj.get("accounts.account[0].id");
        String accountType = "1";

        test.log(Status.INFO, "Create new user");

        Account account =
                given()
                        .contentType(ContentType.JSON).with()
                        .queryParam("customerId", myCustomerId)
                        .queryParam("newAccountType", accountType)
                        .queryParam("fromAccountId", firstAccountId)
                        .auth().basic(username, password)
                        .when()
                        .post(baseURI + "/createAccount")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().as(Account.class);

        System.out.println(account);

        test.log(Status.PASS, "An account has been successfully created: " + account.getId());

    }

    @Test(priority = 5)
    @Tag("Back-End")
    public void testSummaryOfAccount() {
        test = extent.createTest("Account resume");
        test.log(Status.INFO, "Starting test");
        given()
                .when()
                .get("https://parabank.parasoft.com/parabank/overview.htm")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .log()
                .status();
        test.log(Status.PASS, "Verify 200 status response");

    }

    @Test(priority = 6)
    @Tag("Back-End")
    public void testSummaryOfAccountSwagger() {

        test = extent.createTest("APIs endpoints");
        test.log(Status.INFO, "Starting test");
        Customer customer =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(baseURI + "/login/" + username + "/" + password)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().as(Customer.class);

        System.out.println(customer);
        test.log(Status.PASS, "User successfully created by ID: " + customer.getId());
        test.log(Status.INFO, "Getting user account details");


        Response res =
                given()
                        .contentType(ContentType.JSON)
                        .auth().basic(username, password)
                        .when()
                        .get(baseURI + "/customers/" + customer.getId() + "/accounts")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().response();
        XmlPath xmlObj = new XmlPath(res.asString());
        List<String> accountsId = xmlObj.getList("accounts.account");
        System.out.println(accountsId);
        test.log(Status.PASS, "Account details: " + accountsId);

    }

    @Test(priority = 7)
    @Tag("Back-End")
    public void testDownloadFunds() {
        test = extent.createTest("Download resume");
        test.log(Status.INFO, "Starting test");

        Customer customer =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(baseURI + "/login/" + username + "/" + password)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().as(Customer.class);

        System.out.println(customer);
        test.log(Status.PASS, "User successfully logged by ID: " + customer.getId());

        Response res =
                given()
                        .contentType(ContentType.JSON)
                        .auth().basic(username, password)
                        .when()
                        .get(baseURI + "/customers/" + customer.getId() + "/accounts")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().response();
        XmlPath xmlObj = new XmlPath(res.asString());
        List<String> accountsId = xmlObj.getList("accounts.account.id");
        System.out.println(accountsId);

        String firstAccountId = accountsId.get(0);
        String newAccountId = accountsId.get(1);

        test.log(Status.INFO, "Transfer post");

        String amount = "200";
        given()
                .contentType(ContentType.JSON).with()
                .queryParam("fromAccountId", firstAccountId)
                .queryParam("toAccountId", newAccountId)
                .queryParam("amount", amount)
                .auth().basic(username, password)
                .when()
                .post(baseURI + "/transfer")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .log()
                .body();
        test.log(Status.PASS, "Transfer successfully done by ID: " + firstAccountId + " to ID account: " + newAccountId + " total amount: " + amount);


    }

    @Test(priority = 8)
    @Tag("Back-End")
    public void testAccountActivity() {
        test = extent.createTest("Account activity");
        test.log(Status.INFO, "Starting test");


        Customer customer =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(baseURI + "/login/" + username + "/" + password)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().as(Customer.class);

        System.out.println(customer);
        test.log(Status.PASS, "User successfully logged by ID: " + customer.getId());


        Response res =
                given()
                        .contentType(ContentType.JSON)
                        .auth().basic(username, password)
                        .when()
                        .get(baseURI + "/customers/" + customer.getId() + "/accounts")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().response();
        XmlPath xmlObj = new XmlPath(res.asString());
        List<String> accountsId = xmlObj.getList("accounts.account.id");
        System.out.println(accountsId);
        test.log(Status.INFO, "Transfers by account, amount and date");

        String all = "All";
        Response resActivity =
                given()
                        .contentType(ContentType.JSON)
                        .auth().basic(username, password)
                        .when()
                        .get(baseURI + "/accounts/" + accountsId.get(0) + "/transactions/month/" + all + "/type/" + all)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .log()
                        .status().extract().response();

        XmlPath xmlObj2 = new XmlPath(resActivity.asString());
        List<String> transactionsId = xmlObj2.getList("transactions.transaction.id");
        System.out.println(transactionsId);
        test.log(Status.PASS, "Getting transfer details by ID: " + transactionsId);


    }

    @AfterTest
    public void tearDown() {
        extent.flush();
    }
}
