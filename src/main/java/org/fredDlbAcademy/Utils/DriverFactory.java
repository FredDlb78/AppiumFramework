package org.fredDlbAcademy.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.URL;

public class DriverFactory {

    private static AppiumDriver driver;
    private static String deviceName;

    public static AppiumDriver getDriver(String requestedDeviceName) throws Exception {
        if (driver == null) {
            deviceName = requestedDeviceName;
            String platform = DeviceConfigReader.getPlatform(deviceName);

            if ("android".equalsIgnoreCase(platform)) {
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), DeviceConfigReader.getAndroidOptions(deviceName));
            } else if ("ios".equalsIgnoreCase(platform)) {
                driver = new IOSDriver(new URL("http://127.0.0.1:4723"), DeviceConfigReader.getIOSOptions(deviceName));
            } else {
                throw new Exception("Unsupported platform: " + platform);
            }
        }
        return driver;
    }

    public static AppiumDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized. Call getDriver(deviceName) first.");
        }
        return driver;
    }

    public static String getDeviceName() {
        if (deviceName == null) {
            throw new IllegalStateException("Device name not set. Make sure getDriver(deviceName) was called.");
        }
        return deviceName;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            deviceName = null;
        }
    }
}
