package dataproviders;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DataProviders {

    @DataProvider(name = "Validazione CF")
    public Object[][] validazioneCF() throws Exception {
        return ExcelUtil.getTableArray("Validazione CF");
    }

    @DataProvider(name = "DatiCFValidi")
    public Object[][] DatiCFValidi() throws Exception {
        Object[][] test = ExcelUtil.getTableArray("DatiCFValidi");
        return test;
    }

    @DataProvider(name = "Validazione Cognome")
    public Object[][] validazioneCognome() throws Exception {
        Object[][] test = ExcelUtil.getTableArray("Validazione Cognome");
        return test;
    }

    @DataProvider(name = "Validazione Nome")
    public Object[][] validazioneNome() throws Exception {
        Object[][] test = ExcelUtil.getTableArray("Validazione Nome");
        return test;
    }

    @DataProvider(name = "Validazione Tel")
    public Object[][] validazioneTel() throws Exception {
        Object[][] test = ExcelUtil.getTableArray("Validazione Tel");
        return test;
    }

    @DataProvider(name = "Validazione Email")
    public Object[][] validazioneEmail() throws Exception {
        Object[][] test = ExcelUtil.getTableArray("Validazione Email");
        return test;
    }

    @DataProvider(name = "Natura Giuridica")
    public Object[][] naturaGiuridica() throws Exception {
        Object[][] test = ExcelUtil.getTableArray("Natura Giuridica");
        return test;
    }

    @DataProvider(name = "Sesso")
    public Object[][] sesso() throws Exception {
        Object[][] test = ExcelUtil.getTableArray("Sesso");
        return test;
    }

    @DataProvider(name = "Dati PF")
    public Object[][] datiPF() throws Exception {
        Object[][] test = ExcelUtil.getTableArray("Dati PF");
        return test;
    }
}