package fetch.sdetchallenge.steps;

import fetch.sdetchallenge.utils.ConfigReader;
import fetch.sdetchallenge.utils.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import static fetch.sdetchallenge.utils.Driver.getDriver;

public class Hooks {

    private static WebDriver driver;

    @Before
    public void setUp(){
        driver = getDriver();
        getDriver().get(ConfigReader.getPropertiesValue("destinationURL"));
    }

    //take a screenshot if the scenario fails (just for demonstration purposes)
    @After
    public void tearDown(Scenario scenario){
        if (scenario.isFailed()){
            Driver.takeScreenShot(scenario);
        }
        Driver.closeDriver();
    }
}


