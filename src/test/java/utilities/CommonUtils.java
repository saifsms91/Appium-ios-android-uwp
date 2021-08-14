package utilities;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CommonUtils {


    private static AppiumDriver<MobileElement> driver;
    private static URL serverUrl;
    private static DesiredCapabilities capabilities = new DesiredCapabilities();
    private static String APPIUM_PORT;
    private static int IMPLICIT_WAIT_TIME;
    private static int EXPLICIT_WAIT_TIME;
    private static String BASE_PKG;
    private static String APP_ACTIVITY;
    private static String APP_PATH;
    private static String BROWSER_NAME;
    private static String PLATFORM_NAME;
    private static String PLATFORM_VERSION;
    private static String DEVICE_NAME;
    private static String UDID;
    private static String BUNDLE_ID;
    private static String APP;
    private static String APPLICATION_APP;
    private static String AUTOMATION_INSTRUMENTATION;
    private static String AUTOMATION_NAME;


    private static Properties prop = new Properties();
    private static Utils utils = new Utils();
    private static FileInputStream fis;

    //Load Android Configuration File
    public static void loadAndriodConfProp(String loadPropertyFileAndroid) {

        System.out.print("Android Studio");

        prop = utils.prop(loadPropertyFileAndroid);


        IMPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("implicit.wait"));
        EXPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("explicit.wait"));
        BASE_PKG = prop.getProperty("base.pgk");
        APP_ACTIVITY = prop.getProperty("application.activity");
        APP_PATH = prop.getProperty("application.path");
        //BROWSER_NAME = prop.getProperty("browser.name");
        PLATFORM_NAME = prop.getProperty("platform.name");
        PLATFORM_VERSION = prop.getProperty("platform.version");
        DEVICE_NAME = prop.getProperty("device.name");
        APPIUM_PORT = prop.getProperty("appium.server.port");
        AUTOMATION_NAME = prop.getProperty("automation.name");

    }

    //Setting android capabilities
    public static void setAndroidCapabilities(String emulator, String platformName, String platformVersion, String udid, String deviceName, String systemPort, String chromeDriverPort) {

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
//        capabilities.setCapability(CapabilityType.BROWSER_NAME, BROWSER_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, CommonUtils.AUTOMATION_NAME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
//        if(emulator.equalsIgnoreCase("true")) {
//            capabilities.setCapability("avd", deviceName);
//            capabilities.setCapability("avdLaunchTimeout", 120000);
//        }
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, BASE_PKG);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, "True");
        capabilities.setCapability(AndroidMobileCapabilityType.CHROME_OPTIONS, ImmutableMap.of("w3c", false));
        // capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10000);

        // capabilities.setCapability("systemPort", 8210);
//        capabilities.setCapability("chromeDriverPort", chromeDriverPort);
        capabilities.setCapability("chromedriverExecutable", "/usr/local/lib/node_modules/appium/node_modules/appium-chromedriver/chromedriver/mac/chromedriver");
        capabilities.setCapability(MobileCapabilityType.APP, APP_PATH);
//        capabilities.setCapability("showChromedriverLog", true);
        //  capabilities.setCapability("autoGrantPermissions", "true");
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
//        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY,APP_ACTIVITY);
//        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE,BASE_PKG);


    }

    //Getting Android Capabilities
    public static AppiumDriver<MobileElement> getAndroidDriver() {

        try {
            serverUrl = new URL("http://localhost:" + APPIUM_PORT + "/wd/hub");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver = new AndroidDriver<MobileElement>(serverUrl, capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String connectedDeviceName = driver.getSessionDetail("deviceName").toString();
        System.out.print("Saif's " + connectedDeviceName);
        //  driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);

        return driver;

    }


    public static AppiumDriver<MobileElement> getIOSDriver() {
        try {
            serverUrl = new URL("http://localhost:" + APPIUM_PORT + "/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new IOSDriver<MobileElement>(serverUrl, capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;


    }

    public static void loadIOSConfProp(String loadPropertyFileIOS) {
        // TODO Auto-generated method stub

        prop = utils.prop(loadPropertyFileIOS);
        IMPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("implicit.wait"));
        APPIUM_PORT = (prop.getProperty("appium.server.port"));
        APPLICATION_APP = prop.getProperty("application.app");
        UDID = prop.getProperty("udid");
        AUTOMATION_INSTRUMENTATION = prop.getProperty("automation.instrumentation");
        BROWSER_NAME = prop.getProperty("browser.name");
        PLATFORM_NAME = prop.getProperty("platform.name");
        DEVICE_NAME = prop.getProperty("device.name");
        PLATFORM_VERSION = prop.getProperty("platform.version");
        AUTOMATION_NAME = prop.getProperty("automation.name");
    }


    public static AppiumDriver<MobileElement> setIOSCapabilities(String platformName, String platformVersion, String udid, String deviceName, String wdaLocalPort, String webkitDebugProxyPort) {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);

        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, CommonUtils.AUTOMATION_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, udid);
        desiredCapabilities.setCapability("wdaLocalPort", wdaLocalPort);
        desiredCapabilities.setCapability("webkitDebugProxyPort", webkitDebugProxyPort);
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, "True");
        desiredCapabilities.setCapability("automationName", "XCUITest");
        desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "org.askomdch.SwagLabMobileApp");
        desiredCapabilities.setCapability("autoGrantPermissions", "true");
        desiredCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
        desiredCapabilities.setCapability(MobileCapabilityType.APP, CommonUtils.APPLICATION_APP);
        try {
            serverUrl = new URL("http://localhost:" + APPIUM_PORT + "/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new IOSDriver<MobileElement>(serverUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;

    }

    //windows driver setup

    public static AppiumDriver<MobileElement> getWindowDriver() {

        try {
            serverUrl = new URL("http://localhost:" + APPIUM_PORT);
            System.out.println("Saif " + serverUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new WindowsDriver<MobileElement>(serverUrl, capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        return driver;

    }

    public static void loadWindowConfProp(String loadPropertyFileWindow) {
        // TODO Auto-generated method stub

        prop = utils.prop(loadPropertyFileWindow);
        IMPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("implicit.wait"));
        APPIUM_PORT = (prop.getProperty("appium.server.port"));
        BASE_PKG = prop.getProperty("base.pgk");
        BROWSER_NAME = prop.getProperty("browser.name");
        PLATFORM_NAME = prop.getProperty("platform.name");
        DEVICE_NAME = prop.getProperty("device.name");
        PLATFORM_VERSION = prop.getProperty("platform.version");
        AUTOMATION_NAME = prop.getProperty("automation.name");
    }

    public static void setWindowCapabilities() {

        capabilities.setCapability(MobileCapabilityType.APP, BASE_PKG);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, CommonUtils.PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, CommonUtils.DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, CommonUtils.AUTOMATION_NAME);


    }


}
