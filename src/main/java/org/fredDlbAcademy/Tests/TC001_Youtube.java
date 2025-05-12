package org.fredDlbAcademy.Tests;

import org.fredDlbAcademy.Pages.HomePage;
import org.fredDlbAcademy.Utils.BaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC001_Youtube extends BaseTest {

    @Test
    public void TC001() throws IOException {
        HomePage homePage = new HomePage(getDriver(), getDeviceName());
        homePage.clickAcceptNotifications();
    }
}
