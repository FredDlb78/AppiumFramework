package org.fredDlbAcademy.Utils;

import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class DeviceConfigReader {

    private static final String CONFIG_PATH = "src/main/java/org/fredDlbAcademy/utils/devices.json";
    private static JSONObject devicesConfig = null;

    // Méthode pour charger le fichier de configuration JSON
    private static JSONObject loadConfig() throws IOException {
        if (devicesConfig == null) {
            String content = new String(Files.readAllBytes(Paths.get(CONFIG_PATH)));
            devicesConfig = new JSONObject(content);
        }
        return devicesConfig;
    }

    // Méthode pour obtenir les options iOS
    public static XCUITestOptions getIOSOptions(String deviceName) throws IOException {
        JSONObject config = loadConfig().optJSONObject(deviceName);
        if (config == null) {
            throw new IllegalArgumentException("Device configuration for " + deviceName + " not found.");
        }

        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName(config.optString("platform", "iOS"));
        options.setPlatformVersion(config.optString("platformVersion", ""));
        options.setDeviceName(config.optString("deviceName", ""));
        options.setAutomationName(config.optString("automationName", "XCUITest"));

        Optional.ofNullable(config.optString("app", null)).ifPresent(options::setApp);
        Optional.ofNullable(config.optString("bundleId", null)).ifPresent(options::setBundleId);
        Optional.ofNullable(config.optString("udid", null)).ifPresent(options::setUdid);
        Optional.ofNullable(config.optString("xcodeOrgId", null)).ifPresent(v -> options.setCapability("xcodeOrgId", v));
        Optional.ofNullable(config.optString("xcodeSigningId", null)).ifPresent(v -> options.setCapability("xcodeSigningId", v));

        return options;
    }

    // Méthode pour obtenir les options Android
    public static UiAutomator2Options getAndroidOptions(String deviceName) throws IOException {
        JSONObject config = loadConfig().optJSONObject(deviceName);
        if (config == null) {
            throw new IllegalArgumentException("Device configuration for " + deviceName + " not found.");
        }

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(config.optString("platform", "Android"));
        options.setPlatformVersion(config.optString("platformVersion", ""));
        options.setDeviceName(config.optString("deviceName", ""));
        options.setAutomationName(config.optString("automationName", "UiAutomator2"));

        Optional.ofNullable(config.optString("app", null)).ifPresent(options::setApp);
        Optional.ofNullable(config.optString("appPackage", null)).ifPresent(options::setAppPackage);
        Optional.ofNullable(config.optString("appActivity", null)).ifPresent(options::setAppActivity);
        Optional.ofNullable(config.optString("udid", null)).ifPresent(options::setUdid);
        Optional.ofNullable(config.optString("chromedriverExecutable", null)).ifPresent(options::setChromedriverExecutable);

        return options;
    }

    // Méthode pour obtenir la plateforme (iOS ou Android)
    public static String getPlatform(String deviceName) throws IOException {
        JSONObject config = loadConfig().optJSONObject(deviceName);
        if (config == null) {
            throw new IllegalArgumentException("Device configuration for " + deviceName + " not found.");
        }
        return config.optString("platform", "").toLowerCase(); // Ou "platformName" selon ta clé JSON
    }

    // Méthode pour vérifier si l'appareil est Android
    public static boolean isAndroid(String deviceName) {
        try {
            String platform = getPlatform(deviceName);
            return "android".equalsIgnoreCase(platform);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("[DeviceConfigReader] Erreur lors de la vérification de la plateforme Android : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour vérifier si l'appareil est iOS
    public static boolean isIOS(String deviceName) {
        try {
            String platform = getPlatform(deviceName);
            return "ios".equalsIgnoreCase(platform);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("[DeviceConfigReader] Erreur lors de la vérification de la plateforme iOS : " + e.getMessage());
            return false;
        }
    }

}
