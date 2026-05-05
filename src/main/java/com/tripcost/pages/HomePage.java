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

    // --- POPUP LOCATOR ---
    @FindBy(xpath = "//button[@aria-label='Dismiss sign-in info.']")
    WebElement dismissSignIn;

    // --- SEARCH LOCATORS ---
    @FindBy(xpath = "//input[@name='ss']")
    WebElement destinationInput;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement searchButton;

    // --- OCCUPANCY LOCATORS (Provided by you) ---
    @FindBy(xpath = "//button[@data-testid='occupancy-config']")
    WebElement occupancyButton;

    @FindBy(xpath = "(//div[@data-testid='occupancy-popup']/div/div/div/button)[2]")
    WebElement addAdultButton;

    public void dismissPopupIfPresent() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, dismissSignIn, 10);
            dismissSignIn.click();
        } catch (Exception e) {
            System.out.println("Popup didn't appear or couldn't be forced closed: " + e.getMessage());
        }
    }

    public void enterSearchDetails(String dest) {
        WaitUtils.waitForElementToBeVisible(driver, destinationInput, 10);
        destinationInput.sendKeys(dest);
        System.out.println("Typed '" + dest + "' into search box.");

        String dynamicXpath = "//div[text()='" + dest + "']";

        try {
            WebElement suggestion = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath(dynamicXpath)));
            suggestion.click();
            System.out.println("Clicked on the auto-suggest option for: " + dest);
        } catch (Exception e) {
            System.out.println("Auto-suggest dropdown did not appear or wasn't clickable. Proceeding with standard search.");
        }
    }

    public void selectDates() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate checkoutDate = tomorrow.plusDays(5);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String checkinStr = tomorrow.format(formatter);
        String checkoutStr = checkoutDate.format(formatter);

        System.out.println("Selecting Check-in: " + checkinStr);
        System.out.println("Selecting Check-out: " + checkoutStr);

        String checkinXpath = "//*[@data-date='" + checkinStr + "']";
        String checkoutXpath = "//*[@data-date='" + checkoutStr + "']";

        try {
            WebElement checkinElement = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath(checkinXpath)));
            checkinElement.click();

            WebElement checkoutElement = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath(checkoutXpath)));
            checkoutElement.click();

            System.out.println("Dates selected successfully.");
        } catch (Exception e) {
            System.out.println("Calendar selection failed: " + e.getMessage());
        }
    }

    public void selectFourAdults() {
        WaitUtils.waitForElementToBeClickable(driver, occupancyButton, 10);
        occupancyButton.click();
        System.out.println("Opened occupancy dropdown.");

        WaitUtils.waitForElementToBeClickable(driver, addAdultButton, 10);

        for (int i = 0; i < 2; i++) {
            addAdultButton.click();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Successfully selected 4 adults.");
    }

    public void clickSearch() {
        searchButton.click();
    }
}