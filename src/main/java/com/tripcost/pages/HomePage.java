package com.tripcost.pages;

import com.tripcost.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@aria-label='Dismiss sign-in info.']")
    WebElement dismissSignIn;

    @FindBy(xpath = "//input[@name='ss']")
    WebElement destinationInput;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement searchButton;

    @FindBy(xpath = "//button[@data-testid='occupancy-config']")
    WebElement occupancyButton;

    @FindBy(xpath = "(//div[@data-testid='occupancy-popup']/div/div/div/button)[2]")
    WebElement addAdultButton;

    @FindBy(xpath = "(//div[@data-testid='occupancy-popup']/div/div/div/button)[1]")
    WebElement subtractAdultButton;

    public void dismissPopupIfPresent() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, dismissSignIn, 10);
            dismissSignIn.click();
        } catch (Exception e) {
            System.out.println("Popup didn't appear or couldn't be forced closed.");
        }
    }

    public void enterSearchDetails(String dest) {
        WaitUtils.waitForElementToBeVisible(driver, destinationInput, 10);
        destinationInput.sendKeys(dest);

        String dynamicXpath = "//div[text()='" + dest + "']";

        try {
            WebElement suggestion = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath(dynamicXpath)));
            suggestion.click();
        } catch (Exception e) {
            System.out.println("Auto-suggest dropdown did not appear or wasn't clickable.");
        }
    }

    public void selectDates(int daysOfStay) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate checkoutDate = tomorrow.plusDays(daysOfStay);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String checkinStr = tomorrow.format(formatter);
        String checkoutStr = checkoutDate.format(formatter);

        String checkinXpath = "//*[@data-date='" + checkinStr + "']";
        String checkoutXpath = "//*[@data-date='" + checkoutStr + "']";

        try {
            WebElement checkinElement = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath(checkinXpath)));
            checkinElement.click();

            WebElement checkoutElement = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath(checkoutXpath)));
            checkoutElement.click();
        } catch (Exception e) {
            System.out.println("Calendar selection failed.");
        }
    }

    public void selectAdults(int targetAdults) {
        WaitUtils.waitForElementToBeClickable(driver, occupancyButton, 10);
        occupancyButton.click();

        int clicksRequired = targetAdults - 2;

        try {
            if (clicksRequired > 0) {
                WaitUtils.waitForElementToBeClickable(driver, addAdultButton, 10);
                for (int i = 0; i < clicksRequired; i++) {
                    addAdultButton.click();
                    Thread.sleep(500);
                }
            } else if (clicksRequired < 0) {
                WaitUtils.waitForElementToBeClickable(driver, subtractAdultButton, 10);
                for (int i = 0; i < Math.abs(clicksRequired); i++) {
                    subtractAdultButton.click();
                    Thread.sleep(500);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickSearch() {
        searchButton.click();
    }
}