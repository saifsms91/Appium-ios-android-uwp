package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import screens.*;

public class PageLib {

    private AppiumDriver<MobileElement> driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private CommonPageElements commonPageElements;


    public PageLib(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        homePage = new HomePage(this.driver);
        loginPage=new LoginPage(this.driver);
    }


    public HomePage HomePage() {
        return homePage;
    }

    public CommonPageElements CommonPageElements() {
        return commonPageElements;
    }
    public LoginPage LoginPage() {
        return loginPage;
    }



}
