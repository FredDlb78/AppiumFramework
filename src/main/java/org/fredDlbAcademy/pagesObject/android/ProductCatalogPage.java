package org.fredDlbAcademy.pagesObject.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.fredDlbAcademy.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalogPage extends AndroidActions {
    static AndroidDriver driver;

    public ProductCatalogPage(AndroidDriver driver) {
        super(driver);
        ProductCatalogPage.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='ADD TO CART']")
    private static List<WebElement> addToCartButton;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
    private static WebElement cartButton;


    public static void addToCartByIndex(int index) {
        addToCartButton.get(index).click();
    }
    public static CartPage clickCartButton() throws InterruptedException {
        cartButton.click();
        Thread.sleep(2000);
        return new CartPage(driver);
    }
}
