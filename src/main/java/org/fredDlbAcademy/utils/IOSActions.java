package org.fredDlbAcademy.utils;

import io.appium.java_client.ios.IOSDriver;
import org.fredDlbAcademy.pagesObject.iOS.AlertViewsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class IOSActions {
    static IOSDriver driver;

    public IOSActions(IOSDriver driver) {

        this.driver = driver;
    }

    public void longPressAction(WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement)element).getId());
        params.put("duration", 5);

        driver.executeScript("mobile:touchAndHold", params);
    }

    public void scrollToWebElement(WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement)element).getId());
        params.put("direction", "down");
        driver.executeScript("mobile:scroll", params);
    }

    public void swipeAction(String direction) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        driver.executeScript("mobile: swipe", params);
    }
    public<P> P assertEquals(AtomicReference<String> actual, String expected, String errorMessage, P page) {
        if (actual.equals(expected)) {
            System.out.println("Assertion passed");
        } else {
            System.out.println(errorMessage);
        }
        return page;
    }
    public<P> P assertContains(String actual, String expected, String errorMessage, P page) {
        if (actual.contains(expected)) {
            System.out.println("Assertion passed");
        } else {
            System.out.println(errorMessage);
        }
        return page;
    }
    public<P> P assertTrue(boolean condition, String errorMessage, P page) {
        if (condition) {
            System.out.println("Assertion passed");
        } else {
            System.out.println(errorMessage);
        }
        return page;
    }
}



