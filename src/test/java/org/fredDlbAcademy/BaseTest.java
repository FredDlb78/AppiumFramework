package fredDlbAcademy;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.fredDlbAcademy.pagesObject.android.CartPage;
import org.fredDlbAcademy.pagesObject.android.FormPage;
import org.fredDlbAcademy.pagesObject.android.ProductCatalogPage;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

public class BaseTest {
    AndroidDriver driver;
    AppiumDriverLocalService service;

    @BeforeClass
    public void ConfigureAppium() throws MalformedURLException, URISyntaxException {
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File
                        ("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .withIPAddress("127.0.0.1").usingPort(4723).build();
        service.start();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("FredTestEmulator"); //emulator
        //options.setDeviceName("Android Device"); //real device

        options.setChromedriverExecutable("/Users/freddelabre/chromedriver 2 android");
//        options.setApp(
//                "/Users/freddelabre/Automation Projects/Appium_Project/src/test/java/resources/ApiDemos-debug.apk");
        options.setApp(
                "/Users/freddelabre/Automation Projects/Appium_Project/src/test/java/resources/General-Store.apk");

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    public void longPressAction(WebElement element) {
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

    public void waitElementIsVisible(String elementId, String attribute, String value){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeContains(
                driver.findElement(By.id(elementId)), attribute, value));
    }

    public void getFormattedAmount(String amount, AtomicReference<Double> price) {
        Double formattedAmount = Double.parseDouble(amount.substring(1));
        price.set(formattedAmount);

    }

    //adb shell dumpsys window | grep -E 'mCurrentFocus' is the command to get the current activity

    @AfterClass
    public void tearDown() {
        driver.quit();
        service.stop();
    }
}
