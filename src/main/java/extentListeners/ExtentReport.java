package extentListeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.HashMap;
import java.util.Map;

public class ExtentReport {
    static ExtentReports extent;
    final static String filePath = "Extent.html";
    static Map<Integer, ExtentTest> extentTestMap = new HashMap();

    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            ExtentSparkReporter html = new ExtentSparkReporter("Extent.html");
            html.config().setDocumentTitle("Appium Framework");
            html.config().setTheme(Theme.STANDARD);
            html.config().setEncoding("utf-8");
            html.config().setReportName("SauceLabs");
            extent = new ExtentReports();
            extent.attachReporter(html);
            extent = new ExtentReports();
            extent.setSystemInfo("Automation Tester", "Saif Siddiqui");
            extent.setSystemInfo("Organization", "Billennium");
            extent.setSystemInfo("Build no", "4.1.2");
        }

        return extent;
    }

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = getReporter().createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
}
