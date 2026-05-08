package com.tripcost.utils;

import com.tripcost.base.BaseClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static String capture(String testName, boolean passed) {
        try {
            String dir = passed ? ConfigReader.get("screenshot.pass.dir")
                    : ConfigReader.get("screenshot.fail.dir");

            File folder = new File(dir);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String path = dir + testName + "_" + timestamp + ".png";

            File src = ((TakesScreenshot) BaseClass.getDriver()).getScreenshotAs(OutputType.FILE);
            File dest = new File(path);
            FileUtils.copyFile(src, dest);

            return dest.getAbsolutePath();
        } catch (Exception e) {
            return "";
        }
    }
}