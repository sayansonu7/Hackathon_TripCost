package com.tripcost.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver.set(new ChromeDriver());
        }

        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}