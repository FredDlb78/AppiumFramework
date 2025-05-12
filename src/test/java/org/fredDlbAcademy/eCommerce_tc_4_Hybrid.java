package fredDlbAcademy;

import org.fredDlbAcademy.pagesObject.android.CartPage;
import org.fredDlbAcademy.pagesObject.android.FormPage;
import org.fredDlbAcademy.pagesObject.android.ProductCatalogPage;
import org.testng.annotations.Test;



import static org.testng.AssertJUnit.assertEquals;

public class eCommerce_tc_4_Hybrid extends fredDlbAcademy.BaseTest {

    @Test
    public void FillForm() throws InterruptedException {
        FormPage formPage = new FormPage(driver);

        formPage.setName("Fred Dlb");
        formPage.selectGender("male");
        formPage.selectCountry("Argentina");
        formPage.submitForm();
        ProductCatalogPage.addToCartByIndex(0);
        ProductCatalogPage.addToCartByIndex(0);
        ProductCatalogPage.clickCartButton();
        Thread.sleep(1000);
        double totalSum = CartPage.getProductsSum();
        double displayFormattedSum = CartPage.getTotalAmountDisplayed();
        assertEquals(totalSum, displayFormattedSum);
        CartPage.acceptTermsAndContidions();
        CartPage.submitOrder();
    }

}
