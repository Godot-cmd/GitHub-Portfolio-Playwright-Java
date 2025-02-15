package utils;

import base.BaseClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ISuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils extends BaseClass {
    public static void waitForWindowAndSwitch(WebDriver driver, int expectedNumberOfWindows, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
        for (String winHandle : driver.getWindowHandles()) {
            System.out.println(winHandle);
            driver.switchTo().window(winHandle);
        }
    }

    public static FileInputStream allureScr(String screenShotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("dd_MM_yyyy_HHmmss").format(new Date());
        String fullScreenShotName = System.getProperty("user.dir") + "/ErrorScreenshot/" + screenShotName + "_" + timestamp + ".png";
        File destination = new File(fullScreenShotName);
        org.openqa.selenium.io.FileHandler.copy(source, destination);
        return new FileInputStream(destination);
    }

    public static void createSingleReport(ISuite suite) {
        ConfigProperties.initializePropertyFile();
        System.out.println("SUITE NAME: " + suite.getName());
        String batchFilePath = System.getProperty("user.dir")+"\\generate_single_report.cmd";
        String repPath = ConfigProperties.property.getProperty("AllureRepHtml");

        try {
            Process process = Runtime.getRuntime().exec(batchFilePath);

            int exitCode = process.waitFor();
            System.out.println("Batch file executed with exit code: " + exitCode);

            File myFile = new File(repPath);

            int maxWaitTime = 60;
            int waited = 0;
            while (!myFile.exists() && waited < maxWaitTime) {
                Thread.sleep(1000);
                waited++;
            }

            if (myFile.exists()) {
                boolean success = myFile.renameTo(new File("C:/Users/" + System.getProperty("user.name") + "/Desktop/TestFolder/" + suite.getName() + ".html"));
                if (success) {
                    System.out.println("File renamed successfully.");
                } else {
                    System.out.println("Failed to rename the file.");
                }
            } else {
                System.out.println("File was not created within the maximum wait time.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void cleanDirectories(List<File> directories) {

        for (File directory : directories) {
            try {
                if (directory.isDirectory()) {
                    FileUtils.cleanDirectory(directory);
                    System.out.println("Cleaned directory" + directory.getAbsolutePath());
                } else {
                    System.out.println("Skipping non-directory " + directory.getAbsolutePath());
                }
            }catch (IOException e){
                System.err.println("Failed to clean Directory " + directory.getAbsolutePath());
                e.printStackTrace();
            }
        }

    }




}
