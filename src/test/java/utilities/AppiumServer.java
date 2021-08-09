package utilities;

import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServer {

    public static AppiumDriverLocalService service;
   // public static String getLoadPropertyFileEnvironment = "env.properties";
    private static Properties prop = new Properties();
    private static Utils utils = new Utils();
    private static FileInputStream fis;

    //Saif ios

    public static void start() {

        System.out.println(System.getProperty("os.name"));

        if(System.getProperty("os.name").contains("Windows")){
            service = AppiumDriverLocalService.buildService(
                    new AppiumServiceBuilder().usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
//                            .withAppiumJS(new File("C:\\Users\\SaifSiddiqui\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"))
                            .withLogFile(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\logs\\logs.txt"))
                            .withArgument(GeneralServerFlag.LOCAL_TIMEZONE).usingAnyFreePort());
            service.start();

        }else{
            service = AppiumDriverLocalService.buildService(
                    new AppiumServiceBuilder().usingDriverExecutable(new File("/usr/local/bin/node"))
                            .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                            .withLogFile(new File(System.getProperty("user.dir") + "/src/test/resources/logs/mac_logs.txt"))
                            .withArgument(GeneralServerFlag.LOCAL_TIMEZONE));
            service.start();
        }
    }

    public static void stop() {
        if (service != null) {
            service.stop();
            System.out.println("Appium Server Stopped");
        }

    }

}
