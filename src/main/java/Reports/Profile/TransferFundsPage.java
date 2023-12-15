package Reports.Profile;

import Reports.Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransferFundsPage extends BasePage {

    By inputUsername = By.xpath("//input[@name='username']");
    By inputPassword = By.xpath("//input[@name='password']");
    By btnLogIn = By.xpath("//input[@value='Log In']");
    By transferFundsMenu = By.xpath("//a[normalize-space()='Transfer Funds']");
    By title = By.className("title");
    By inputAmount = By.id("amount");
    By selectDestinationAccount = By.id("toAccountId");
    By btnTransfer = By.xpath("//input[@value='Transfer']");
    By message = By.xpath("//h1[normalize-space()='Transfer Complete!']");

    public TransferFundsPage(WebDriver driver, WebDriverWait wait) {
    }

    public String result() throws InterruptedException {
        Thread.sleep(2000);
        String res = findElement(message).getText();
        System.out.println("Page content result: " + res);
        return res;
    }

    public String checkTitle() throws InterruptedException {
        Thread.sleep(2000);
        String res = findElement(title).getText();
        System.out.println("Page content title: " + res);
        return res;
    }

    public void clickTransferFundsMenu() throws InterruptedException {
        click(transferFundsMenu);
    }

    public void completeForm(String amount) throws InterruptedException {
        writeInput(amount, inputAmount);
        Select destinationAccount = new Select(driver.findElement(selectDestinationAccount));
        destinationAccount.selectByValue("13677");
    }

    public void clickEnterBtn() throws InterruptedException {
        Thread.sleep(2000);
        click(btnTransfer);
    }

    public void login(String username, String password) throws InterruptedException {
        writeInput(username, inputUsername);
        writeInput(password, inputPassword);
        click(btnLogIn);
    }
}
