package org.fredDlbAcademy.pagesObject.iOS;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.fredDlbAcademy.utils.IOSActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.atomic.AtomicReference;

public class AlertViewsPage extends IOSActions {
    IOSDriver driver;

    public AlertViewsPage(IOSDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name == \"Text Entry\"`]")
    private WebElement textEntryMenu;
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell")
    private WebElement textEntryField;
    @iOSXCUITFindBy(accessibility = "OK")
    private WebElement okButton;
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name BEGINSWITH \"Confirm\"`]")
    private WebElement confirmCancelButton;
    @iOSXCUITFindBy(accessibility = "Confirm")
    private WebElement confirmButton;
    @iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH[c] 'A message'")
    private WebElement confirmMessage;

    public AlertViewsPage clickTextEntryButton() {
        textEntryMenu.click();
        return this;
    }
    public AlertViewsPage setTextEntryField(String text) {
        textEntryField.click();
        textEntryField.clear();
        textEntryField.sendKeys(text);
        return this;
    }
    public AlertViewsPage clickOkButton() {
        okButton.click();
        return this;
    }
    public AlertViewsPage clickConfirmCancelButton() {
        confirmCancelButton.click();
        return this;
    }
    public AlertViewsPage clickConfirmButton() {
        confirmButton.click();
        return this;
    }
    public AlertViewsPage getConfirmMessage(AtomicReference<String> strRef) {
        String message = confirmMessage.getText();
        strRef.set(message);
        return this;
    }

}
