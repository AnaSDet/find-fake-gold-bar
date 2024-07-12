package fetch.sdetchallenge.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuPage extends BasePage{

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    //result btn button
    @FindBy(xpath = "//button[contains(text(),'Reset')]")
    protected WebElement resetButton;

    @FindBy(xpath = "//li")
    protected WebElement firstResult;

    @FindBy(xpath = "//li[2]")
    protected WebElement secondResult;


    public void setResetButtonClick(){
        resetButton.click();
    }
    public String getFirstResult(){
        return firstResult.getText();
    }
    public String getSecondResult(){
        return secondResult.getText();
    }
}
