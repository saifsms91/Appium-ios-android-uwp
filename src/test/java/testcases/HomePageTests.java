package testcases;

import base.TestBase;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class HomePageTests extends TestBase {


    @BeforeTest
    public void init() throws IOException, InterruptedException {

    }


    @Test(priority = 1)
    public void verifyHomePageElements() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();

    }


    @AfterTest
    public void quitDriver() {
        System.out.print("Quit Driver");
        quit();
    }
}
