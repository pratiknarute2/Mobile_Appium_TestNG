package utility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class Driver {
    public static UiAutomator2Options uiAutomator2Options;
    public static XCUITestOptions xcuiTestOptions;
    public static AppiumDriver driver;
    public static String deviceName;

    public static void quitDriver() throws InterruptedException {
        Thread.sleep(5000); // Wait for 5 seconds before quitting
        if (driver != null) {
            try {
                if (driver.getSessionId() != null) {
                    driver.quit();
                    System.out.println("Quit driver successfully");
                }
            } catch (Exception e) {
                System.out.println("Exception occurred while quitting the driver: " + e.getMessage());
            }
        } else {
            System.out.println("Driver is already null.");
        }
    }

    public static void launchIOSApplication(String deviceName, String platformVersion, String appiumPort) throws MalformedURLException {
        xcuiTestOptions = xcuiTestOptions_for_iOS(deviceName, platformVersion);
        driver = new AppiumDriver(new URL("http://127.0.0.1:" + appiumPort), xcuiTestOptions);
        System.out.println("iOS App Launched!");
    }

    public static void launchAndroidApplication() throws MalformedURLException {
        System.setProperty("ANDROID_HOME", "/Users/testingautomation/Library/Android/sdk");
        System.setProperty("ANDROID_SDK_ROOT", "/Users/testingautomation/Library/Android/sdk");

        uiAutomator2Options = uiAutomator2Options_for_android();
        driver = new AppiumDriver(new URL("http://127.0.0.1:4723"), uiAutomator2Options);
        System.out.println("Android App Launched!");
    }

    public static UiAutomator2Options uiAutomator2Options_for_android() {
        HashMap dc = new HashMap();
        dc = new utility.DesiredCapabilities(deviceName).getDesiredCapabilitiesForAndroidEmulator();
        System.out.println("Desired capability for Android: \n"+dc);
        System.out.println(":-------------------------------------------------------------------------------------------------------:");

        uiAutomator2Options = new UiAutomator2Options()
                .setDeviceName(String.valueOf(dc.get("deviceName")))
                .setPlatformName(String.valueOf(dc.get("platformName")))
                .setPlatformVersion(String.valueOf(dc.get("platformVersion")))
                .setAppPackage(String.valueOf(dc.get("appPackage")))
                .setAppActivity(String.valueOf(dc.get("appActivity")))
                .setNoReset(((Boolean) dc.get("noReset")))
                .setFullReset((Boolean) dc.get("fullReset"))
                .setAutoGrantPermissions((Boolean) dc.get("setAutoGrantPermissions"));

        return uiAutomator2Options;
    }

    public static XCUITestOptions xcuiTestOptions_for_iOS(String deviceName, String platformVersion) {
        xcuiTestOptions = new XCUITestOptions();
        xcuiTestOptions.setPlatformName("iOS");
        xcuiTestOptions.setPlatformVersion(platformVersion);
        xcuiTestOptions.setDeviceName(deviceName);
        xcuiTestOptions.setAutomationName("XCUITest");
        xcuiTestOptions.setApp("/Users/testingautomation/Lyca/Mobile_Automation/src/main/resources/App/TestApp.app");
        xcuiTestOptions.setUseNewWDA(true);
        xcuiTestOptions.setAutoAcceptAlerts(true);
        xcuiTestOptions.setWdaLaunchTimeout(Duration.ofSeconds(60));
        xcuiTestOptions.setNewCommandTimeout(Duration.ofSeconds(60));
        xcuiTestOptions.setClearSystemFiles(true);

        return xcuiTestOptions;
    }

}
