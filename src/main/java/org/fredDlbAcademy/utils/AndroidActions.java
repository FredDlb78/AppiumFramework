package org.fredDlbAcademy.utils;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

public class AndroidActions {
    static AndroidDriver driver;

    public AndroidActions(AndroidDriver driver) {
        this.driver = driver;
    }

    public static void longPressAction(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "duration", 2000));
    }

    public void scrollToElementAndClick(String elementName) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + elementName + "\"));")).click();
    }

    public void scrollToEndAction() {
        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 3.0
            ));
        } while (canScrollMore);
    }

    public void swipeToElementAction(WebElement element, String direction) {
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", 0.75
        ));
    }

    public void dragDropAction(WebElement source, Integer destinationX, Integer destinationY) {
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) source).getId(),
                "endX", destinationX,
                "endY", destinationY
        ));
    }

    public void rotateToPortraitMode() {
        DeviceRotation portrait = new DeviceRotation(0, 0, 0);
        driver.rotate(portrait);
    }

    public void rotateToLandScapeMode() {
        DeviceRotation landscape = new DeviceRotation(0, 0, 90);
        driver.rotate(landscape);
    }

    public void copyToClipboard(String text) {
        driver.setClipboardText(text);
    }

    public void goToPackage(String packageName) {
        ((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of("intent", packageName));
    }

    public void getToastMessage(String toastMessageExpected, AtomicReference<String> toastMessage) {
        String pageSource = driver.getPageSource();
        if (pageSource.contains(toastMessageExpected)) {
            toastMessage.set(toastMessageExpected);
        }
    }

    public void hideKeyboard() {
        driver.hideKeyboard();
    }
    }
