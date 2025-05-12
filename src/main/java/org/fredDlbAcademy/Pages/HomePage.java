package org.fredDlbAcademy.Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.fredDlbAcademy.Utils.DeviceConfigReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.NoSuchElementException;

import java.io.IOException;

public class HomePage {

    private AppiumDriver driver;
    private String deviceName;

    public HomePage(AppiumDriver driver, String deviceName) throws IOException {
        this.driver = driver;
        this.deviceName = this.deviceName;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.android.permissioncontroller:id/permission_allow_button\")")
    @iOSXCUITFindBy(accessibility = "//TODO")  // Remplacer par l'identifiant correct pour iOS
    private WebElement acceptNotificationsBtn;

    public void clickAcceptNotifications() {
        try {
            if (DeviceConfigReader.isAndroid(deviceName)) {
                System.out.println("Clicking on Accept Notifications button on Android");
                acceptNotificationsBtn.click();
                System.out.println("Clicked on Accept Notifications button on Android");
            } else {
                System.out.println("There is no Accept Notifications button on iOS");
            }

        } catch (NoSuchElementException e) {
            System.out.println("Accept Notifications button not found: " + e.getMessage());
        }
    }
}