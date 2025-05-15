package org.fredDlbAcademy.Utils;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MobileActions {
    private final AppiumDriver driver;
    private final String platform;

    public MobileActions(AppiumDriver driver) {
        this.driver = driver;
        Object platformCap = driver.getCapabilities().getCapability("platformName");
        this.platform = (platformCap != null) ? platformCap.toString().toLowerCase() : "";
    }

    // ===== Android Actions =====
    public void longPressAndroid(WebElement element) {
        if (!platform.equals("android")) return;
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "duration", 2000));
    }

    public void scrollToElementAndClickAndroid(String elementName) {
        if (!platform.equals("android")) return;
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + elementName + "\"));")).click();
    }

    public void scrollToEndAndroid() {
        if (!platform.equals("android")) return;
        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 3.0
            ));
        } while (canScrollMore);
    }

    public void swipeToElementAndroid(WebElement element, String direction) {
        if (!platform.equals("android")) return;
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", 0.75
        ));
    }

    public void dragDropAndroid(WebElement source, int x, int y) {
        if (!platform.equals("android")) return;
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) source).getId(),
                "endX", x,
                "endY", y
        ));
    }

    public void rotatePortraitAndroid() {
        if (!platform.equals("android")) return;
        ((AndroidDriver) driver).rotate(new DeviceRotation(0, 0, 0));
    }

    public void rotateLandscapeAndroid() {
        if (!platform.equals("android")) return;
        ((AndroidDriver) driver).rotate(new DeviceRotation(0, 0, 90));
    }

    public void goToPackage(String packageName) {
        if (!platform.equals("android")) return;
        ((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of("intent", packageName));
    }

    // ===== iOS Actions =====
    public void longPressIOS(WebElement element) {
        if (!platform.equals("ios")) return;
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) element).getId());
        params.put("duration", 5);
        driver.executeScript("mobile:touchAndHold", params);
    }

    public void scrollToElementIOS(WebElement element) {
        if (!platform.equals("ios")) return;
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) element).getId());
        params.put("direction", "down");
        driver.executeScript("mobile:scroll", params);
    }

    public void swipeIOS(String direction) {
        if (!platform.equals("ios")) return;
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        driver.executeScript("mobile:swipe", params);
    }

    public void copyToClipboard(String text) {
        if (platform.equals("android")) {
            ((AndroidDriver) driver).setClipboardText(text);
        } else if (platform.equals("ios")) {
            ((IOSDriver) driver).setClipboardText(text);
        }
    }

    public void hideKeyboard() {
        if (platform.equals("android")) {
            ((AndroidDriver) driver).hideKeyboard();
        } else if (platform.equals("ios")) {
            ((IOSDriver) driver).hideKeyboard();
        }
    }

    public void getToastMessage(String expected, AtomicReference<String> output) {
        String pageSource = driver.getPageSource();
        if (pageSource.contains(expected)) {
            output.set(expected);
        }
    }

    public <P> P assertEquals(AtomicReference<String> actual, String expected, String errorMessage, P page) {
        if (actual.get().equals(expected)) {
            System.out.println("Assertion passed");
        } else {
            System.out.println(errorMessage);
        }
        return page;
    }

    public <P> P assertContains(String actual, String expected, String errorMessage, P page) {
        if (actual.contains(expected)) {
            System.out.println("Assertion passed");
        } else {
            System.out.println(errorMessage);
        }
        return page;
    }

    public <P> P assertTrue(boolean condition, String errorMessage, P page) {
        if (condition) {
            System.out.println("Assertion passed");
        } else {
            System.out.println(errorMessage);
        }
        return page;
    }
}
