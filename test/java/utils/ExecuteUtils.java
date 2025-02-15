package utils;

import io.qameta.allure.Allure;
import org.testng.Assert;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class ExecuteUtils {

    public static void executeTestTrueNoParam(String excelFilePath, int testId, String elementCheckDescription, boolean elementCheck) {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(excelFilePath, testId);

        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        try {
            Assert.assertTrue(elementCheck, elementCheckDescription + " non viene visualizzato");
        } catch (AssertionError e) {
            Allure.addAttachment(testName, elementCheckDescription + " non viene visualizzato");
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        }

        Allure.addAttachment("Expected Results", expectedResults);
    }


    public static void executeTestRicerca(String excelFilePath, int testId, String failureMessage, Supplier<Boolean> testAction) throws InterruptedException {
        executeTestRicerca(excelFilePath, testId, failureMessage, testAction, null);
    }

    public static void executeTestRicerca(String excelFilePath, int testId, String failureMessage, Supplier<Boolean> testAction, Runnable finallyAction) throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(excelFilePath, testId);

        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        try {
            Assert.assertTrue(testAction.get(), failureMessage);
        } catch (AssertionError e) {
            Allure.addAttachment(testName, failureMessage);
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        } finally {
            if (finallyAction != null) {
                finallyAction.run();
            }
        }

        Allure.addAttachment("Expected Results", expectedResults);
    }


    public static void executePreferitiTest(String excelFilePath, int testId, String failureMessage, Supplier<Boolean> testAction) throws InterruptedException {
        executePreferitiTest(excelFilePath, testId, failureMessage, testAction, null);
    }

    public static void executePreferitiTest(String excelFilePath, int testId, String failureMessage, Supplier<Boolean> testAction, Runnable finallyAction) throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(excelFilePath, testId);

        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        try {
            Assert.assertTrue(testAction.get(), failureMessage);
        } catch (AssertionError e) {
            Allure.addAttachment(testName, failureMessage);
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        } finally {
            if (finallyAction != null) {
                finallyAction.run();
            }
        }

        Allure.addAttachment("Expected Results", expectedResults);
    }


    public static void executeSchedeRecentiTest(String excelFilePath, int testId, String failureMessage, Supplier<Boolean> testAction) throws InterruptedException {
        executeSchedeRecentiTest(excelFilePath, testId, failureMessage, testAction, null);
    }

    public static void executeSchedeRecentiTest(String excelFilePath, int testId, String failureMessage, Supplier<Boolean> testAction, Runnable finallyAction) throws InterruptedException {
        Map<String, String> testData = ExcelUtil.RetrieveTestData(excelFilePath, testId);

        String testName = testData.get("TestName");
        String testDescription = testData.get("TestDescription");
        String expectedResults = testData.get("ExpectedResults");

        AllureUtils.updateAllureReport(testName, testDescription);

        try {
            Assert.assertTrue(testAction.get(), failureMessage);
        } catch (AssertionError e) {
            Allure.addAttachment(testName, failureMessage);
            throw e;
        } catch (Exception e) {
            Allure.addAttachment(testName, "Si è verificato un errore imprevisto durante il verifica della visibilità DBX: \n" + e.getMessage());
            throw e;
        } finally {
            if (finallyAction != null) {
                finallyAction.run();
            }
        }

        Allure.addAttachment("Expected Results", expectedResults);
    }

}
