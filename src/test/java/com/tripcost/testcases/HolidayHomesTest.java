package com.tripcost.testcases;

import com.tripcost.base.BaseClass;
import com.tripcost.pages.HomePage;
import com.tripcost.pages.HolidayHomesPage;
import com.tripcost.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class HolidayHomesTest extends BaseClass {

    @DataProvider(name = "holidayData")
    public Object[][] getHolidayData() throws IOException {
        String excelPath = System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx";
        return ExcelUtils.getExcelData(excelPath, "Sheet1");
    }

    @Test(dataProvider = "holidayData", groups = {"Smoke", "Regression"})
    public void verifyHolidayHomesExtraction(String id, String testCaseName, String destination, String adultsStr, String daysStr, String primaryFilter, String secondaryFilter, String status) {

        if (testCaseName == null || (!testCaseName.trim().equalsIgnoreCase("VerifyHolidayHomes") && !testCaseName.toLowerCase().contains("testcase"))) {
            throw new SkipException("Skipped row");
        }

        int rowNum = 1;
        String excelPath = System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx";

        try {
            rowNum = Integer.parseInt(id.replace("TC", ""));

            int adults = (adultsStr != null && !adultsStr.isEmpty()) ? (int) Double.parseDouble(adultsStr) : 2;
            int days = (daysStr != null && !daysStr.isEmpty()) ? (int) Double.parseDouble(daysStr) : 5;

            HomePage homePage = new HomePage(getDriver());

            homePage.dismissPopupIfPresent();
            homePage.enterSearchDetails(destination);
            homePage.selectDates(days);
            homePage.selectAdults(adults);
            homePage.clickSearch();

            HolidayHomesPage holidayHomesPage = new HolidayHomesPage(getDriver());

            boolean customSortApplied = false;

            if (primaryFilter != null && !primaryFilter.trim().isEmpty()) {
                if (primaryFilter.toLowerCase().contains("price") || primaryFilter.toLowerCase().contains("reviewed")) {
                    holidayHomesPage.applyDynamicSort(primaryFilter);
                    customSortApplied = true;
                } else {
                    holidayHomesPage.applyDynamicFilter(primaryFilter);
                }
            }

            if (secondaryFilter != null && !secondaryFilter.trim().isEmpty()) {
                if (secondaryFilter.toLowerCase().contains("price") || secondaryFilter.toLowerCase().contains("reviewed")) {
                    holidayHomesPage.applyDynamicSort(secondaryFilter);
                    customSortApplied = true;
                } else {
                    holidayHomesPage.applyDynamicFilter(secondaryFilter);
                }
            }

            if (!customSortApplied) {
                holidayHomesPage.applyDynamicSort("Property rating (high to low)");
            }

            holidayHomesPage.getTopThreeResults(days);

            ExcelUtils.setCellData(excelPath, "Sheet1", rowNum, 7, "PASS");

        } catch (Exception e) {
            try {
                ExcelUtils.setCellData(excelPath, "Sheet1", rowNum, 7, "FAIL");
            } catch (Exception excelError) {
                System.out.println("Could not write FAIL status to Excel.");
            }
            Assert.fail(e.getMessage());
        }
    }
}