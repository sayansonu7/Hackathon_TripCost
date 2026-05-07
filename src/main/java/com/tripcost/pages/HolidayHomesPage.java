package com.tripcost.pages;

import com.tripcost.utils.WaitUtils;
import org.openqa.selenium.By;
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

    public void applyDynamicSort(String sortText) {
        if (sortText == null || sortText.trim().isEmpty()) {
            return;
        }
        try {
            WaitUtils.waitForElementToBeClickable(driver, sortDropdown, 15);
            sortDropdown.click();

            String dynamicSortXpath = "//span[text()='" + sortText + "'] | //span[contains(text(), '" + sortText + "')] | //button[contains(@aria-label, '" + sortText + "')]";

            WebElement sortOption = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(By.xpath(dynamicSortXpath)));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", sortOption);

            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println("Could not apply sorting: " + sortText);
        }
    }

    public void applyDynamicFilter(String filterName) {
        if (filterName == null || filterName.trim().isEmpty()) {
            return;
        }

        scrollToFilters();

        String dynamicFilterXpath = "//div[contains(text(), '" + filterName + "')]/preceding-sibling::input | //div[contains(text(), '" + filterName + "')]";

        try {
            WebElement filterElement = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(By.xpath(dynamicFilterXpath)));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", filterElement);

            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Could not apply filter: " + filterName);
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