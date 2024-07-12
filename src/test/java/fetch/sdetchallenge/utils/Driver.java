package fetch.sdetchallenge.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.cucumber.java.Scenario;

import java.time.Duration;

public class Driver {
    private static WebDriver driver;

    private Driver() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            try {
                String browser = ConfigReader.getPropertiesValue("browser");
                switch (browser.toLowerCase()) {
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driver = new FirefoxDriver();
                        break;
                    case "safari":
                        WebDriverManager.safaridriver().setup();
                        driver = new SafariDriver();
                        break;
                    case "ie":
                        WebDriverManager.iedriver().setup();
                        driver = new InternetExplorerDriver();
                        break;
                    case "headless":
                        WebDriverManager.chromedriver().browserVersion("126.0.6478.127").setup();
                        ChromeOptions headlessOptions = new ChromeOptions();
                        headlessOptions.addArguments("--headless");
                        headlessOptions.addArguments("--window-size=1920,1080");
                        headlessOptions.addArguments("--disable-extensions");
                        headlessOptions.addArguments("--proxy-server='direct://'");
                        headlessOptions.addArguments("--proxy-bypass-list=*");
                        headlessOptions.addArguments("--start-maximized");
                        headlessOptions.addArguments("--remote-allow-origins=*");
                        driver = new ChromeDriver(headlessOptions);
                        break;
                    default:
                    case "chrome":
                        WebDriverManager.chromedriver().browserVersion("126.0.6478.127").setup();
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--remote-allow-origins=*");
                        driver = new ChromeDriver(options);
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error initializing WebDriver: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            driver.manage().window().maximize();
        } else {
            System.err.println("WebDriver initialization failed.");
        }

        return driver;
    }

    public static void takeScreenShot(Scenario scenario) {
        if (scenario.isFailed()) {
            //taking a screenshot and saving it in byte arrays.
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            //adding the screenshot to the report
            scenario.attach(screenshot, "image/png", "screenshot");
        }
    }

    //Create a method that quits the driver
    //which should check if instance is already instantiated once
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}