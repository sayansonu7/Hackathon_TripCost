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
    public void verifyHolidayHomesExtraction(String colA, String testCaseName, String destination, String resultCol) {
        if (!testCaseName.trim().equalsIgnoreCase("VerifyHolidayHomes")) {
            throw new SkipException("Skipped row: Not applicable for Holiday Homes test");
        }

        try {
            HomePage homePage = new HomePage(getDriver());

            homePage.dismissPopupIfPresent();
            homePage.enterSearchDetails(destination);
            homePage.selectDates();
            homePage.selectFourAdults();
            homePage.clickSearch();

            System.out.println("Search executed. Moving to Results Page...");

            HolidayHomesPage holidayHomesPage = new HolidayHomesPage(getDriver());
            holidayHomesPage.applyBookingFilters();
            holidayHomesPage.getTopThreeResults();

            String excelPath = System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx";
            ExcelUtils.setCellData(excelPath, "Sheet1", 1, 3, "PASS");

        } catch (Exception e) {
            try {
                String excelPath = System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx";
                ExcelUtils.setCellData(excelPath, "Sheet1", 1, 3, "FAIL");
            } catch (Exception excelError) {
                System.out.println("Could not write FAIL status to Excel.");
            }
            Assert.fail(e.getMessage());
        }
    }
}