package com.tripcost.pages;

import com.tripcost.utils.WaitUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class HolidayHomesPage {
    WebDriver driver;

    public HolidayHomesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@data-testid='sorters-dropdown-trigger']")
    WebElement sortDropdown;

    @FindBy(xpath = "//button[@aria-label='Property rating (high to low)']")
    WebElement highestRatingSort;

    @FindBy(xpath = "//div[contains(text(), 'Elevator')]")
    WebElement elevatorFilterLabel;

    @FindBy(xpath = "//div[contains(text(), 'Elevator')]/preceding-sibling::input | //input[@name='popular_activities=11']")
    WebElement elevatorCheckbox;

    @FindBy(xpath = "//div[@data-testid='title']")
    List<WebElement> hotelNames;

    @FindBy(xpath = "//span[@data-testid='price-and-discounted-price']")
    List<WebElement> totalPrices;

    @FindBy(xpath = "//div[contains(@data-testid, 'price-per-night')] | //div[contains(text(),'per night')]")
    List<WebElement> perNightPrices;

    public void scrollToFilters() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,600)", "");
    }

    public void applyBookingFilters() {
        WaitUtils.waitForElementToBeClickable(driver, sortDropdown, 15);
        sortDropdown.click();

        WaitUtils.waitForElementToBeClickable(driver, highestRatingSort, 10);
        highestRatingSort.click();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scrollToFilters();

        try {
            WaitUtils.waitForElementToBeVisible(driver, elevatorFilterLabel, 15);
            try {
                if (!elevatorCheckbox.isSelected()) {
                    elevatorCheckbox.click();
                }
            } catch (Exception innerE) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", elevatorCheckbox);
            }
        } catch (Exception e) {
            System.out.println("Could not apply Elevator filter.");
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getTopThreeResults() {
        int count = Math.min(3, hotelNames.size());
        for (int i = 0; i < count; i++) {
            System.out.println("Property " + (i + 1) + ": " + hotelNames.get(i).getText());
            System.out.println("Total Amount: " + totalPrices.get(i).getText());

            if (i < perNightPrices.size()) {
                System.out.println("Charges Per Night: " + perNightPrices.get(i).getText());
            } else {
                System.out.println("Charges Per Night: Data not found on card.");
            }
            System.out.println("---------------------------------------");
        }
    }
}