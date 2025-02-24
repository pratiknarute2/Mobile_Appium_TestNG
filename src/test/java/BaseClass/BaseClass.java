package BaseClass;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pom.LoginPage;
import utility.Driver;
import utility.Utility;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseClass extends Utility {
    String OS = "Android";
    String deviceName = "5554";
    private static ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setupExtentReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    @BeforeClass
    public void login() throws InterruptedException, MalformedURLException {
        Driver.deviceName = deviceName;
        if (OS.equals("Android")) {
            launchAndroidApplication();
        }
        new LoginPage().login();
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        quitDriver();
    }

//    @AfterMethod
//    public void attachScreenshot(ITestResult result) {
//        test = extent.createTest(result.getMethod().getMethodName());
//
//        if (result.getStatus() == ITestResult.FAILURE) {
//            String screenshotPath = takeScreenshot(result.getMethod().getMethodName());
//
//            // Debugging: Print Screenshot Path
//            System.out.println("Screenshot saved at: " + screenshotPath);
//
//            File file = new File(screenshotPath);
//            if (file.exists()) {
//                try {
//                    // Ensure path uses double backslashes in Windows
//                    String correctedPath = screenshotPath.replace("\\", "/");
//
//                    // Attach Screenshot to Extent Report
//                    test.fail("Screenshot of failure",
//                            MediaEntityBuilder.createScreenCaptureFromPath(correctedPath).build());
//
//                    System.out.println("✅ Screenshot attached successfully!");
//                } catch (Exception e) {
//                    test.fail("❌ Failed to attach screenshot: " + e.getMessage());
//                }
//            } else {
//                test.fail("❌ Screenshot NOT found at: " + screenshotPath);
//            }
//
//            test.fail("Test Failed: " + result.getThrowable());
//        }
//    }



//    // Method to take a screenshot
//    public String takeScreenshot(String methodName) {
//        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
//        File directory = new File(screenshotDir);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        String screenshotPath = screenshotDir + methodName + "_" + timestamp + ".png";
//        File destination = new File(screenshotPath);
//
//        try {
//            FileUtils.copyFile(src, destination);
//            System.out.println("✅ Screenshot captured: " + screenshotPath);
//        } catch (IOException e) {
//            System.out.println("❌ Failed to save screenshot: " + e.getMessage());
//        }
//
//        return destination.getAbsolutePath();
//    }



    @AfterSuite
    public void tearDownExtentReport() {
//        extent.flush();  // ✅ Finalize report
    }

}
