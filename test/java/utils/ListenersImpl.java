package utils;

import base.BaseClass;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Allure;
import org.testng.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;


public class ListenersImpl extends BaseClass implements ITestListener, ISuiteListener {

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }


    @Override
    public void onTestFailure(ITestResult result) {
        try {
            FileInputStream screenshotInputStream = Utils.allureScr(result.getName());
            Allure.addAttachment(result.getName() + " Screenshot", screenshotInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }

    @Override
    public void onStart(ISuite suite) {

        ConfigProperties.initializePropertyFile();
        List<File> directories = List.of(
                new File(ConfigProperties.property.getProperty("AllureRepDir")),
                new File(ConfigProperties.property.getProperty("AllureResDir")),
                new File(ConfigProperties.property.getProperty("ErrScreenDir"))
        );

        Utils.cleanDirectories(directories);
    }

    @Override
    public void onFinish(ISuite suite) {

        JsonUtil.createExecutorJson(suite.getName());
        allureEnvironmentWriter(

                ImmutableMap.<String, String>builder()
                        .put("Sistema operativo:", SystemInfoUtil.getOperatingSystemName())
                        .put("Ambiente:", driver.getTitle())
                        .put("Versione Ambiente:", VersionUtil.getFileVersion(ConfigProperties.property.getProperty("App")))
                        .build(),
                System.getProperty("user.dir") + "/allure-results/");

        XmlUtil.mergeXmlFiles();
        Utils.createSingleReport(suite);

        driver.quit();

    }
}
