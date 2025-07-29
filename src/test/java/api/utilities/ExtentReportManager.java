package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName; // Variable to store the report file name

    @Override
    public void onStart(ITestContext testContext) {
        // Generate timestamp for unique report name
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        repName = "Test_Report_" + timeStamp + ".html";

        // Specify location of the report
        sparkReporter = new ExtentSparkReporter("./reports/" + repName);

        // Configure Spark Reporter
        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject"); // Title of report
        sparkReporter.config().setReportName("Pet Store Users API"); // Name of the report
        sparkReporter.config().setTheme(Theme.DARK); // Theme of the report (DARK or STANDARD)

        // Create ExtentReports instance and attach the Spark Reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Set system information for the report
        extent.setSystemInfo("Application", "Pet Store Users API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("user", "pavan"); // Example user info
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Create a new test entry in the report for a passed test
        test = extent.createTest(result.getName()); // Test name is the method name
        test.assignCategory(result.getMethod().getGroups()); // Assign categories based on TestNG groups
        test.createNode(result.getName()); // Create a node for the test
        test.log(Status.PASS, "Test Passed"); // Log test status as PASS
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Create a new test entry in the report for a failed test
        test = extent.createTest(result.getName()); // Test name is the method name
        test.assignCategory(result.getMethod().getGroups()); // Assign categories
        test.createNode(result.getName()); // Create a node
        test.log(Status.FAIL, "Test Failed"); // Log test status as FAIL
        test.log(Status.FAIL, result.getThrowable().getMessage()); // Log the exception/error message
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Create a new test entry in the report for a skipped test
        test = extent.createTest(result.getName()); // Test name is the method name
        test.assignCategory(result.getMethod().getGroups()); // Assign categories
        test.createNode(result.getName()); // Create a node
        test.log(Status.SKIP, "Test Skipped"); // Log test status as SKIP
        test.log(Status.SKIP, result.getThrowable().getMessage()); // Log the skip reason/exception
    }

    @Override
    public void onFinish(ITestContext testContext) {
        // Flush the report to write all information to the HTML file
        extent.flush();
    }

    // Other ITestListener methods (onTestStart, onTestFailedButWithinSuccessPercentage, onStart)
    // are not explicitly shown in the provided screenshots but are part of the interface.
    // They can be implemented as empty methods or with specific logging if needed.

    @Override
    public void onTestStart(ITestResult result) {
        // Not explicitly shown in screenshots, but part of ITestListener
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not explicitly shown in screenshots, but part of ITestListener
    }
}
