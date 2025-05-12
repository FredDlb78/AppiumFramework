package org.fredDlbAcademy;

import org.fredDlbAcademy.pagesObject.iOS.AlertViewsPage;
import org.fredDlbAcademy.pagesObject.iOS.HomePage;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicReference;

public class IOSBasics extends IOSBaseTest {

    @Test
    public void IOSBasics() {
        AtomicReference<String> strRef = new AtomicReference<>();

        HomePage homePage = new HomePage(driver);
        AlertViewsPage alertViewsPage = new AlertViewsPage(driver);

        homePage.clickAlertViewsMenu()
                .clickTextEntryButton()
                .setTextEntryField("Hello World")
                .clickOkButton()
                .clickConfirmCancelButton()
                .getConfirmMessage(strRef)
                .assertEquals(strRef, "A message should be a short, complete sentence.", "Button text is not correct", alertViewsPage)
                .clickConfirmButton();
    }
}
