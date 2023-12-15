package Reports.Profile;

import Reports.Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountActivityPage extends BasePage {

    By inputUsername = By.xpath("//input[@name='username']");
    By inputPassword = By.xpath("//input[@name='password']");
    By btnLogIn = By.xpath("//input[@value='Log In']");
    By menuAccountOverview = By.xpath("//a[normalize-space()='Accounts Overview']");
    By successMessage = By.xpath("//a[normalize-space()='Balance includes deposits that may be subject to holds']");
    By title = By.cssSelector("td[colspan='3']");
    By account = By.xpath("//a[normalize-space()='13677']");
    By titleAccountDetails = By.xpath("//h1[normalize-space()='Account Details']");

    By period = By.id("Month");
    By type = By.id("transactionType");
    By allPeriod = By.id("Month/option[1]");
    By allType = By.id("TransactionType/option[1]");
    By btnGo = By.xpath("//input[@value='Go']");

    public AccountActivityPage(WebDriver driver, WebDriverWait wait) {
    }

    public String checkTitleAccountDetails() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(titleAccountDetails));
        WebElement element = driver.findElement(titleAccountDetails);
        String res = element.getText();
        System.out.println("Page content result: " + res);
        return res;
    }

    public String checkTitle() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(title));
        WebElement element = driver.findElement(title);
        String res = element.getText();
        System.out.println("Page content title: " + res);
        return res;
    }

    public void clickAccountOverview() throws InterruptedException {
        click(menuAccountOverview);
    }

    public void selectAccount() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(account));
        WebElement element = driver.findElement(account);
        click(account);
    }


    public String getMessage() throws InterruptedException {
        Thread.sleep(2000);
        String res = findElement(successMessage).getText();
        return res;
    }

    public String getPeriod() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(period));
        WebElement element = driver.findElement(period);
        String res = element.getText();
        System.out.println("Period selected: " + res);
        return res;
    }

    public String selectType() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(type));
        WebElement element = driver.findElement(type);
        String res = element.getText();
        System.out.println("Selected type: " + res);
        return res;
    }


    public String getAllPeriod() throws InterruptedException {
        Thread.sleep(2000);
        String res = findElement(allPeriod).getText();
        return res;
    }

    public String getAllType() throws InterruptedException {
        Thread.sleep(2000);
        String res = findElement(allType).getText();
        return res;
    }


    public void clickGo() throws InterruptedException{
        Thread.sleep(2000);
        click(btnGo);
    }
}
