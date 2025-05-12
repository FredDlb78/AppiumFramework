package org.fredDlbAcademy.pagesObject.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.fredDlbAcademy.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.PageFactory;

public class FormPage extends AndroidActions {
    AndroidDriver driver;

    public FormPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
    private WebElement maleOption;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
    private WebElement femaleOption;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
    private WebElement countryDropdown;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement shopButton;


    public void setName(String name) {
        nameField.sendKeys(name);
    }

    public void selectGender(String gender) {
        if (gender.contains("female")) {
            femaleOption.click();
        } else {
            maleOption.click();
        }
    }

    public void selectCountry(String countryName) {
        countryDropdown.click();
        scrollToElementAndClick(countryName);
    }

    public ProductCatalogPage submitForm() {
        shopButton.click();
        return new ProductCatalogPage(driver);
    }

}
