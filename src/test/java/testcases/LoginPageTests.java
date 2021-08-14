package testcases;

import base.TestBase;
import org.json.JSONTokener;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.InputStream;


public class LoginPageTests extends TestBase {
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws Exception {
        InputStream datais = null;
        try {
            String dataFileName = "data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);

            loginUsers = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (datais != null) {
                datais.close();
            }
        }
        closeApp();
        launchApp();
    }

    @Test
    public void invalidUserName() {
        App().Pages().LoginPage().enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
        App().Pages().LoginPage().enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
        App().Pages().LoginPage().pressLoginBtn();

        String actualErrTxt = App().Pages().LoginPage().getErrTxt();
       // String expectedErrTxt = getStrings().get("err_invalid_username_or_password");
        String expectedErrTxt ="Username and password do not match any user in this service.";
        Assert.assertEquals(actualErrTxt, expectedErrTxt);
    }

    @Test
    public void invalidPassword() {
        App().Pages().LoginPage().enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
        App().Pages().LoginPage().enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
        App().Pages().LoginPage().pressLoginBtn();

      String actualErrTxt = App().Pages().LoginPage().getErrTxt();
        //String expectedErrTxt = getStrings().get("err_invalid_username_or_password");
        String expectedErrTxt ="Username and password do not match any user in this service.";

        Assert.assertEquals(actualErrTxt, expectedErrTxt);
    }


    @AfterTest
    public void quitDriver() {
        System.out.print("Quit Driver");
        quit();
    }

}
