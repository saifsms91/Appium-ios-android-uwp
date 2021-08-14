package base;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;


import lib.AppLib;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utilities.AppiumServer;
import utilities.CommonUtils;
import utilities.Utils;

public class TestBase {
    protected static ThreadLocal<AppiumDriver> driver2 = new ThreadLocal<AppiumDriver>();
    public AppiumDriver<MobileElement> driver;
    public static String loadPropertyFileAndroid = "Android_App.properties";
    public static String loadPropertyFileIOS = "IOS_App.properties";
    public static String loadPropertyFileWindow = "Window_App.properties";
    public static String getLoadPropertyFileEnvironment = "env.properties";
    public static String getUsername = "username.properties";
    public static String getPassword = "password.properties";


    public static Logger log = Logger.getLogger(TestBase.class);
    public static String getPlatform;
    public static boolean enabled = true;
    private static Utils utils = new Utils();
    private static Properties prop = new Properties();
    private static FileInputStream fis;
    ProcessBuilder builder;
    Process startStopWinAppDriver;

    //    protected static ThreadLocal <AppiumDriver> driverAppium = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal<Properties> props = new ThreadLocal<Properties>();
    protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
    protected static ThreadLocal<String> platform = new ThreadLocal<String>();
    protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
    protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
    URL serverUrl;
    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    public AppiumDriver getDriver() {
        return driver2.get();
    }

    public void setDriver(AppiumDriver driver3) {
        driver2.set(driver3);
    }

    @FindBy(id = "Button_RightAction")
    public WebElement homeButton;

    private AppLib app;

    public AppLib App() {
        return app;
    }


    @Parameters({"emulator", "platformName", "platformVersion", "udid", "deviceName", "systemPort",
            "chromeDriverPort", "wdaLocalPort", "webkitDebugProxyPort"})
    @BeforeTest
    public void beforeTest(@Optional("androidOnly") String emulator, @Optional("androidOnly") String platformName, @Optional("androidOnly") String platformVersion, @Optional("androidOnly") String udid, @Optional("androidOnly") String deviceName,
                           @Optional("androidOnly") String systemPort, @Optional("androidOnly") String chromeDriverPort,
                           @Optional("iOSOnly") String wdaLocalPort, @Optional("iOSOnly") String webkitDebugProxyPort) throws Exception {
        if (driver == null) {
            prop = utils.prop(getLoadPropertyFileEnvironment);
            getPlatform = prop.getProperty("env.properties");

            PropertyConfigurator
                    .configure(System.getProperty("user.dir") + "/src/test/resources/properties/log4j.properties");
            setPlatform(platformName);
            switch (System.getProperty("platformName")) {

                case "iOS":
                    if (loadPropertyFileIOS.startsWith("IOS_")) {
                        AppiumServer.start();
                        log.info("Appium server started");
                        CommonUtils.loadIOSConfProp(loadPropertyFileIOS);
                        log.info(loadPropertyFileIOS + " properties file loaded !!!");
                        //CommonUtils.setIOSCapabilities();

                        driver = CommonUtils.setIOSCapabilities(platformName, platformVersion, udid, deviceName, wdaLocalPort, webkitDebugProxyPort);
                        setDriver(driver);
                    }
                    break;

                case "Windows":
                    if (loadPropertyFileWindow.startsWith("Window_")) {
                        //AppiumServer.start();
                        String command = "C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe";
                        builder = new ProcessBuilder(command).inheritIO();
                        startStopWinAppDriver = builder.start();
                        log.info("Appium server started");
                        CommonUtils.loadWindowConfProp(loadPropertyFileWindow);
                        log.info(loadPropertyFileWindow + " properties file loaded !!!");
                        CommonUtils.setWindowCapabilities();
                        driver = CommonUtils.getWindowDriver();

                    }
                    break;

                default:
//                    if(checkIfServerIsRunnning(4723)){
//                        AppiumServer.stop();
//                    }
                    if (loadPropertyFileAndroid.startsWith("Android_")) {


                        //  AppiumServer.stop();
                        AppiumServer.start();
                        CommonUtils.loadAndriodConfProp(loadPropertyFileAndroid);
                        log.info(loadPropertyFileAndroid + " properties file loaded !!!");
                        //CommonUtils.setAndroidCapabilities();
                        CommonUtils.setAndroidCapabilities(emulator, platformName, platformVersion, udid, deviceName, systemPort, chromeDriverPort);
                        driver = CommonUtils.getAndroidDriver();
                        setDriver(driver);

                    }
            }

            app = new AppLib(driver);


        }

    }


    public HashMap<String, String> getStrings() {
        return strings.get();
    }

    public void setStrings(HashMap<String, String> strings2) {
        strings.set(strings2);
    }
//
//    public String getPlatform() {
//        //System.getProperty("platformName");
//        return String.valueOf(System.getProperty("platformName"));
//        // return platform.get();
//    }

    public void setPlatform(String platform2) {
        System.setProperty("platformName",platform2);
     //   platform.set(platform2);
    }

    public String getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(String dateTime2) {
        dateTime.set(dateTime2);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }


    public void quit() {
        System.out.println("Drive close method");
        getDriver().quit();
        log.info("Test case execution completed");
        System.out.println("Drive close method 2");
        log.info("Appium server stopped !!!");

    }


    //Transition to Android WebView
    public void changeDriverContextToWeb() throws InterruptedException {
        Thread.sleep(2000);
        if (getPlatform.equalsIgnoreCase("Android")) {
            Set<String> allContext = driver.getContextHandles();
            System.out.println(allContext);
            for (String context : allContext) {
                if (context.contains("WEBVIEW"))
                    driver.context("WEBVIEW_com");
                //driver.context(context);
            }
        }

    }

    public void changeDriverContextToNative() throws InterruptedException {
        Thread.sleep(2000);
        if (getPlatform.equalsIgnoreCase("Android")) {
            driver.context("NATIVE_APP");

        }

    }


//    @BeforeTest
//    public void init() throws IOException, InterruptedException {
//
//        setUp();
//        loginIntoApp();
//        InputStream stringsis = null;
//
//        try {
//            String xmlFileName = "strings/strings.xml";
//            //testUtil.log().info("load " + xmlFileName);
//            stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
//            setStrings(testUtil.parseStringXML(stringsis));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    public void closeApp() {
        driver.closeApp();
    }

    public void launchApp() {
        driver.launchApp();
    }



}
