package utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class ScrollUtil {

	
	
	
	public static AndroidElement scrollToTextByAndroidUIAutomator(String text, AndroidDriver driver) {
		
		
		  return (AndroidElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
		 +text+"\").instance(0))");
		 
	}
	
	//Scroll to a particular element
	
	public static AndroidElement scrollToElement(By by,AndroidDriver driver) {
		AndroidElement element = null;
		int numberOfTimes = 10;
		Dimension size = driver.manage().window().getSize();
		int anchor = (int)(size.width / 2);
		//Swipe up to scroll down
		int startPoint = (int)(size.height - 10);
		int endPoint = 10;

		for (int i = 0; i < numberOfTimes; i++) {
		try {
		new TouchAction(driver)
		.longPress(PointOption.point(anchor, startPoint)) //.press(point(anchor, startPoint)) if used press need proper waiting time
		//.waitAction(waitOptions(ofMillis(miliseconds)))
		.moveTo(PointOption.point(anchor, endPoint)).release().perform();
		element = (AndroidElement) driver.findElement(by);
		i = numberOfTimes;
		} catch (NoSuchElementException ex) {
		System.out.println(String.format("Element not available. Scrolling (%s) times�", i + 1));
		}
		}
		return element;
		}
	
	
	
	
	public static void scrollDown(AndroidDriver driver) {
	    //if pressX was zero it didn't work for me
	    int pressX = driver.manage().window().getSize().width / 2;
	    // 4/5 of the screen as the bottom finger-press point
	    int bottomY = driver.manage().window().getSize().height * 4/5;
	    // just non zero point, as it didn't scroll to zero normally
	    int topY = driver.manage().window().getSize().height / 8;
	    //scroll with TouchAction by itself
	    scroll(pressX, bottomY, pressX, topY,driver);
	}



	public static void scroll(int fromX, int fromY, int toX, int toY, AndroidDriver driver) {
	    TouchAction touchAction = new TouchAction(driver);
	    touchAction.longPress(PointOption.point(fromX, fromY)).moveTo(PointOption.point(toX, toY)).release().perform();
	}
	
	
	
	public static void scrollUp(int howManySwipes,AppiumDriver<MobileElement> driver) {
		Dimension size = driver.manage().window().getSize();
		// calculate coordinates for vertical swipe
		int startVerticalY = (int) (size.height * 0.8);
		int endVerticalY = (int) (size.height * 0.21);
		int startVerticalX = (int) (size.width / 2.1);
		  try {
		      for (int i = 1; i <= howManySwipes; i++) {
						new TouchAction(driver).press(PointOption.point(startVerticalX, startVerticalY))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(startVerticalX, endVerticalY)).release()
								.perform();
					}
				} catch (Exception e) {
					//print error or something
				}
	}
	
	
	
	public static void scrollDown(int howManySwipes,AppiumDriver<MobileElement> driver) {
		Dimension size = driver.manage().window().getSize();
		// calculate coordinates for vertical swipe
		int startVerticalY = (int) (size.height * 0.8);
		int endVerticalY = (int) (size.height * 0.21);
		int startVerticalX = (int) (size.width / 2.1);
		  try {
		      for (int i = 1; i <= howManySwipes; i++) {
						new TouchAction(driver).press(PointOption.point(startVerticalX, endVerticalY))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(startVerticalX, startVerticalY)).release()
								.perform();
					}
				} catch (Exception e) {
					//print error or something
				}
		  
		  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
