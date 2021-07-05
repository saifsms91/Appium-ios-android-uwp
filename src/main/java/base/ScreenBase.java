package base;

import com.aventstack.extentreports.Status;

import extentListeners.ExtentReport;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.log4j.LogSF;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ScreenBase {


    public static AppiumDriver<MobileElement> driver;
    public WebDriverWait wait;
    public static Logger log = Logger.getLogger(ScreenBase.class);

    public ScreenBase(AppiumDriver<MobileElement> driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 10);
    }

    public void waitForVisibility(WebElement el) {
        System.out.println("*****  Executing waitForVisibility method  *****");
        wait.until(ExpectedConditions.visibilityOf(el));
    }

    public void waitForInVisibility(WebElement el) {
        System.out.println("*****  Executing waitForVisibility method  *****");
        wait.until(ExpectedConditions.invisibilityOf(el));

    }

    public void waitForVisibilityFluent(WebElement el) {
        wait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(el));
    }

    public void clear(WebElement el) {
        waitForVisibility(el);
        el.clear();
    }

    public void click(WebElement el) {
        System.out.println(this.wait);
        waitForVisibility(el);
        el.click();
    }

    public void click(WebElement el, String msg) {
        waitForVisibility(el);

//        utils.log().info(msg);
       // ExtentListeners.testReport.get().log(Status.INFO, msg);
        ExtentReport.getTest().log(Status.INFO, msg);
        log.info(msg);

        el.click();
    }

    public void sendKeys(WebElement el, String txt) {
        waitForVisibility(el);
        el.sendKeys(txt);
    }

    public void sendKeys(WebElement el, String txt, String msg) {
        waitForVisibility(el);
//        utils.log().info(msg);
       // ExtentListeners.testReport.get().log(Status.INFO, msg);
        ExtentReport.getTest().log(Status.INFO, msg);

        el.sendKeys(txt);
    }

    public String getAttribute(WebElement el, String attribute) {
        waitForVisibility(el);
        return el.getAttribute(attribute);
    }

    public String getText(WebElement el, String msg, String getPlatform) {
        System.out.println("Platform " + driver.getPlatformName());
        String txt = null;
        switch (getPlatform) {
            case "Android":
                txt = getAttribute(el, "text");
                break;
            case "iOS":
                txt = getAttribute(el, "label");
                break;
        }
//        utils.log().info(msg + txt);
      //  ExtentListeners.testReport.get().log(Status.INFO, msg + txt);

        ExtentReport.getTest().log(Status.INFO, msg + txt);
        log.info(msg + txt);

        return txt;
    }

    public void hideKeyboard() {
        driver.hideKeyboard();
    }

    public void enter() {
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));

    }

}
