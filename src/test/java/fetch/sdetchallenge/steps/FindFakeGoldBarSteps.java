package fetch.sdetchallenge.steps;

import fetch.sdetchallenge.pages.MenuPage;
import fetch.sdetchallenge.utils.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindFakeGoldBarSteps {
    //private WebDriver driver;
    WebDriver driver = Driver.getDriver();
    private MenuPage menuPage = new MenuPage(driver);

    private List<String> weighings = new ArrayList<>();

    @Given("I open the gold bar game website")
    public void i_open_the_gold_bar_game_website() {
        WebDriverManager.chromedriver().setup();
        // driver = new ChromeDriver();
        // driver = WebDriverManager.getDriver();
        //driver.get("http://sdetchallenge.fetch.com/");
    }


    @When("I identify the fake gold bar")
    public void i_identify_the_fake_gold_bar() throws InterruptedException {
        int fakeBar = findFakeGoldBar();
        System.out.println(("Fake bar: " + fakeBar));
        driver.findElement(By.id("coin_" + fakeBar)).click();
    }

    @Then("I should see the correct alert message")
    public void i_should_see_the_correct_alert_message() {
        Alert alert = driver.switchTo().alert();
        String alertMessage = alert.getText();
        alert.accept();
        System.out.println(alertMessage);
        assertEquals("Yay! You find it!", alertMessage);
    }

    @Then("I print the number of weighings and list of weighings")
    public void i_print_the_number_of_weighings_and_list_of_weighings() {
        System.out.println("Number of weighings: " + weighings.size());
        for (String weighing : weighings) {
            System.out.println(weighing);
        }
    }

    private void resetScale() {
        menuPage.setResetButtonClick();
        //driver.findElement(By.xpath("//button[contains(text(),'Reset')]")).click();
    }
    private void weighBars(int[] left, int[] right) throws InterruptedException {
        resetScale();
        System.out.println("Weighing bars: Left - " + arrayToString(left) + " | Right - " + arrayToString(right));
        for (int i = 0; i < left.length; i++) {
            if (left[i] != -1) {
                driver.findElement(By.id("left_" + i)).sendKeys(String.valueOf(left[i]));
            }
        }
        for (int i = 0; i < right.length; i++) {
            if (right[i] != -1) {
                driver.findElement(By.id("right_" + i)).sendKeys(String.valueOf(right[i]));
            }
        }
        driver.findElement(By.id("weigh")).click();
        Thread.sleep(2000);
        if(left.length <= 1){
            WebElement resultElement2 = driver.findElement(By.xpath("//li[2]"));
            String result2 = resultElement2.getText();
            System.out.println(result2);

            weighings.add(result2);
            System.out.println("Weighing result: " + result2);
        }else{
            WebElement resultElement = driver.findElement(By.xpath("//li"));
            String result = resultElement.getText();

            weighings.add(result);
            System.out.println("Weighing result: " + result);
        }
    }
    private String getWeighingResult1() {
        String result1 = menuPage.getFirstResult();
        return result1;

        // return driver.findElement(By.xpath("//li")).getText();
    }
    private String getWeighingResult2() {

        String result2 = menuPage.getSecondResult();
        return result2;
        //return driver.findElement(By.xpath("//li[2]")).getText();
    }
    private int findFakeGoldBar() throws InterruptedException {
        // Step 1: Divide into groups
        int[] group1 = {0, 1, 2};
        int[] group2 = {3, 4, 5};
        int[] group3 = {6, 7, 8};

        // Step 2: Weigh the first two groups
        weighBars(group1, group2);
        String result = getWeighingResult1();
        System.out.println(result);

        int[] suspectGroup;

        if (result.contains("<")) {
            // Group1 is lighter, fake bar is in group1
            suspectGroup = group1;
        } else if (result.contains(">")) {
            // Group2 is lighter, fake bar is in group2
            suspectGroup = group2;
        } else {
            // Groups are equal, fake bar is in group3
            suspectGroup = group3;
        }
        Thread.sleep(2000);
        // Step 3: Identify the fake bar in the suspect group
        weighBars(new int[]{suspectGroup[0]}, new int[]{suspectGroup[1]});
        result = getWeighingResult2();

        if (result.contains("<")) {
            return suspectGroup[0];
        } else if (result.contains(">")) {
            return suspectGroup[1];
        } else {
            return suspectGroup[2];
        }
    }
    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            if (i != -1) {
                sb.append(i).append(" ");
            }
        }
        return sb.toString().trim();
    }
}
