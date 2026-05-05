package com.tripcost.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class JavascriptUtils {
    public static WebElement javaScriptExecutorWithScrollUtils(WebDriver driver, WebElement webElement, int timeoutInSeconds) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', behavior: 'instant'});",
                    webElement
            );
            return WaitUtils.waitForElementToBeClickable(driver, webElement, timeoutInSeconds);
        } catch (Exception e) {
            throw e;
        }
    }
}

