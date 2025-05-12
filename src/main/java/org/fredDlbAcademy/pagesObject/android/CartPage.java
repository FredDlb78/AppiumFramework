package org.fredDlbAcademy.pagesObject.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.fredDlbAcademy.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AndroidActions {
    AndroidDriver driver;

    public CartPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    @AndroidFindBy (id = "com.androidsample.generalstore:id/productPrice")
    private static List<WebElement> productList;
    @AndroidFindBy (id = "com.androidsample.generalstore:id/totalAmountLbl")
    private static WebElement totalAmount;
    @AndroidFindBy (id = "com.androidsample.generalstore:id/termsButton")
    private static WebElement termsButton;
    @AndroidFindBy (id = "android:id/button1")
    private static WebElement closeButton;
    @AndroidFindBy (id = "com.androidsample.generalstore:id/btnProceed")
    private static WebElement proceedButton;
    @AndroidFindBy (className = "android.widget.CheckBox")
    private static WebElement checkBox;

    public List<WebElement> getProductList() {
        return productList;
    }
    public static double getProductsSum() {
        int count = productList.size();
        double totalSum = 0;
        for (int i = 0; i < count; i++) {
            String amountString = productList.get(i).getText();
            Double price = getFormattedAmount(amountString);
            totalSum = totalSum + price;
        }
        return totalSum;
    }

    public static double getTotalAmountDisplayed() {
        return getFormattedAmount(totalAmount.getText());
    }
    public static void acceptTermsAndContidions() {
        longPressAction(termsButton);
        closeButton.click();
    }
    public static Double getFormattedAmount(String amountString) {
        Double price = Double.parseDouble(amountString.substring(1));
        return price;
    }
    public static void submitOrder() {
        checkBox.click();
        proceedButton.click();
    }


}
