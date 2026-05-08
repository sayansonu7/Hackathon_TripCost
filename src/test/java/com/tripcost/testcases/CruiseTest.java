package com.tripcost.testcases;

import com.tripcost.base.BaseClass;
import com.tripcost.models.CruiseInfo;
import com.tripcost.pages.TripadvisorCruisePage;
import com.tripcost.utils.ExcelUtils;
import com.tripcost.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class CruiseTest extends BaseClass {

    @DataProvider(name = "cruiseData")
    public Object[][] getCruiseData() throws IOException {
        String excelPath = System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx";
        // Pointing directly to the new Cruises sheet
        return ExcelUtils.getExcelData(excelPath, "Cruises");
    }

    @Test(dataProvider = "cruiseData", groups = {"Regression"})
    public void verifyCruiseShipDetails(String id, String testCaseName, String targetUrl, String adultsStr, String daysStr, String primaryFilter, String secondaryFilter, String status) {

        int rowNum = 1;
        String excelPath = System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx";

        try {
            rowNum = Integer.parseInt(id.replace("TC", ""));

            System.out.println("Executing Cruise Test " + id + ": " + targetUrl);

            TripadvisorCruisePage cruisePage = new TripadvisorCruisePage(getDriver());
            cruisePage.open(targetUrl);

            CruiseInfo shipInfo = cruisePage.fetchCruiseInfo();

            System.out.println("Ship Name: " + shipInfo.getShipName());
            System.out.println("Passengers: " + shipInfo.getPassengers());
            System.out.println("Crew: " + shipInfo.getCrew());
            System.out.println("Launched Year: " + shipInfo.getLaunched());

            System.out.println("Languages:");
            for (String language : shipInfo.getLanguages()) {
                System.out.println("- " + language);
            }

            Assert.assertNotEquals(shipInfo.getPassengers(), "N/A", "Failed to extract passenger data.");

            // Writing to the new Cruises sheet
            ExcelUtils.setCellData(excelPath, "Cruises", rowNum, 7, "PASS");

        } catch (Exception e) {
            try {
                // Writing to the new Cruises sheet
                ExcelUtils.setCellData(excelPath, "Cruises", rowNum, 7, "FAIL");
            } catch (Exception excelError) {
                System.out.println("Could not write FAIL status to Excel.");
            }
            Assert.fail(e.getMessage());
        }
    }

}