package Reports;
import com.aventstack.extentreports.ExtentReports;

public class ExtentFactory {
        public static ExtentReports getInstance(String selenium_version, String s) {
            ExtentReports extent = new ExtentReports();
            extent.setSystemInfo("Environment", "PROD");
            extent.setSystemInfo("OS", "Windows");
            extent.setSystemInfo("Browser", "Chrome");
            return extent;
        }
}
