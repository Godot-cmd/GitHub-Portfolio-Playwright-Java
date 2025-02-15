package tests.DBX634;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pom.anagrafe.AnagrafeCercaResultsPage;
import pom.anagrafe.AnagrafeMainPage;
import utils.ExcelUtil;

public class CercaCognome extends BaseClass {

    public final String provider_name = "cognomeData";

    @DataProvider(name = provider_name)
    public Object[][] cognomeData() throws Exception {
        Object[][] test = ExcelUtil.getTableArray("Cognome");
        return test;
    }

    @Test(dataProvider = provider_name, priority = 1)
    public void cercaCognomefromDropDown(String cercaValue, String expectedValue, String type) throws InterruptedException {
        AnagrafeMainPage anagrafeMainPage = new AnagrafeMainPage(page);
        anagrafeMainPage.cerca(cercaValue);

        try {
            Assert.assertTrue(anagrafeMainPage.isFullNameFromDropdownVisible(expectedValue), expectedValue+ " Cognome from DropDown is not Visible " + type);
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    @Test(dataProvider = provider_name, priority = 2)
    public void cercaCognome(String cercaValue, String expectedCognomeValue, String type) throws InterruptedException {

        AnagrafeMainPage anagrafePom = new AnagrafeMainPage(page);
        anagrafePom.cerca(cercaValue);
        anagrafePom.clickCercaBtn();

        AnagrafeCercaResultsPage anagrafeCercaResultsPage = new AnagrafeCercaResultsPage(page);
        try {
            Assert.assertTrue(anagrafeCercaResultsPage.isCognomeVisible(expectedCognomeValue), "Cognome from RESULTATI is not Visible " + type);
        }finally {
            anagrafeCercaResultsPage.clickIndietro();
        }
    }

}