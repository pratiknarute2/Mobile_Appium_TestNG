package BaseClass;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pom.LoginPage;
import utility.Driver;
import utility.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseClass extends Utility {
    String OS = "Android";
    String deviceName = "5554";

    public static AppiumDriverLocalService service;
    public static ExtentReports report;
    public static ExtentTest test;
    public static ExtentSparkReporter spark;
    String screenshotPath = getCurrentDirectory()+"/screenshots/failed.png";
    public static String appiumJsPath="";

    @BeforeSuite
    public void setupExtentReport() throws IOException {
        System.out.println("Screenshot path: "+screenshotPath);
		System.out.println("Conncected device: "+getDeviceName());
		System.out.println("AppiumJs Path: "+getAppiumJsPath());

        report = new ExtentReports(); // To generate extent report
        spark = new ExtentSparkReporter("Extent Report/report.html");  //Set path in system for attach the generated report
        report.attachReporter(spark); //To attach generated report into selec
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
//        service.stop();
    }

    @AfterMethod
    public void attachScreenshot(ITestResult result) throws IOException, InterruptedException {
        test = report.createTest(result.getMethod().getMethodName())
                .assignCategory(result.getTestClass().getName())
                .assignAuthor("Pratik")
                .assignDevice("Windows");

        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotFile = System.getProperty("user.dir") + "/screenshots/" + result.getMethod().getMethodName() + ".png";
            File drag = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dropPath = new File(screenshotFile);
            FileHandler.copy(drag, dropPath);

            // Wait for the file to be written
            Thread.sleep(1000);

            if (dropPath.exists()) {
//                test.fail("Test case failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotFile).build());
                String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                test.fail("Test case failed", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

            } else {
                test.fail("Screenshot not found!");
            }

            test.log(Status.FAIL, result.getThrowable());
        } else {
            test.log(Status.PASS, "Test case passed");
        }
        report.flush();
    }




    @AfterSuite
    public void tearDownExtentReport() {
    }

    public  String getDeviceName() throws IOException {
        try {
            // Execute the adb command to list connected devices
            Process process = Runtime.getRuntime().exec("adb devices");

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("deviceName: "+reader.readLine());
            boolean devicesListStarted = false;
            while ((line = reader.readLine()) != null) {
                if (devicesListStarted && !line.isEmpty() && !line.startsWith("*")) {
                    // Extract device name from the output
                    deviceName = line.split("\\s+")[0];
                }
                if (!devicesListStarted && line.contains("List of devices attached")) {
                    devicesListStarted = true;
                }
            }

            // Close the reader
            reader.close();

            // Wait for the process to finish
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceName;
    }
    public static String getAppiumJsPath() throws IOException {
        try {
            // Execute the where appium command to know the dir
            Process process = Runtime.getRuntime().exec("where appium");

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String commandOutput = reader.readLine();
            System.out.println("commandOutput: "+commandOutput);
            appiumJsPath=commandOutput.replace("/bin/appium", "/lib/node_modules/appium/build/lib/main.js");
            System.out.println("appiumJsPath: "+appiumJsPath);

            // Close the reader
            reader.close();

            // Wait for the process to finish
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appiumJsPath;
    }

}
