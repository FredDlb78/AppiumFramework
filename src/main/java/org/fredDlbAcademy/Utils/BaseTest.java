package org.fredDlbAcademy.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class BaseTest {
    protected AppiumDriver driver;
    protected String deviceName = "S23James";  // deviceName centralisé
    private AppiumDriverLocalService service;

    @BeforeClass
    public void ConfigureAppium() throws MalformedURLException, URISyntaxException, IOException {
        // Obtenir le chemin vers Appium via la variable d’environnement
        String appiumJSPath = System.getenv("APPIUM_HOME");
        if (appiumJSPath == null || appiumJSPath.isEmpty()) {
            throw new RuntimeException("La variable d’environnement APPIUM_HOME n’est pas définie. Définis-la avec le chemin vers main.js");
        }

        // Démarrage du service Appium
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File(appiumJSPath))
                // .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")) // ligne pour ton pote sur Mac
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();

        // Charger le fichier devices.json
        FileReader reader = new FileReader("src/main/java/org/fredDlbAcademy/utils/devices.json");
        Gson gson = new Gson();
        JsonObject devicesConfig = gson.fromJson(reader, JsonObject.class);

        // Extraire la plateforme de l'appareil spécifié
        String platform = devicesConfig.getAsJsonObject(deviceName).get("platform").getAsString();

        // Configuration Android
        if ("ANDROID".equalsIgnoreCase(platform)) {
            UiAutomator2Options androidOptions = DeviceConfigReader.getAndroidOptions(deviceName);
            driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), androidOptions);
        }
        // Configuration iOS
        else if ("IOS".equalsIgnoreCase(platform)) {
            XCUITestOptions iosOptions = DeviceConfigReader.getIOSOptions(deviceName);
            driver = new IOSDriver(new URI("http://127.0.0.1:4723").toURL(), iosOptions);
        } else {
            throw new IllegalArgumentException("Platform not found for device: " + deviceName);
        }

        // Timeout implicite
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public String getDeviceName() {
        return deviceName;
    }
}
