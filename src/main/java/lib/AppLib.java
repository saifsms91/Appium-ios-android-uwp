package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppLib {

    private AppiumDriver<MobileElement> driver;
    private PageLib page;


    public AppLib(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        page = new PageLib(this.driver);

    }

    public PageLib Pages() {
        return page;
    }
}
