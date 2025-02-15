package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {

    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    private static XSSFRow Row;

    public static Object[][] getTableArray(String SheetName) throws Exception {

        String[][] tabArray = null;

        try {
            ConfigProperties.initializePropertyFile();
            FileInputStream ExcelFile = new FileInputStream(ConfigProperties.property.getProperty("TestData"));

            // Access the required test data sheet
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(SheetName);

            int startRow = 1;
            int startCol = 0;

            int ci, cj;

            int totalRows = ExcelWSheet.getLastRowNum() + 1;
            int totalCols = ExcelWSheet.getRow(0).getLastCellNum();

            tabArray = new String[totalRows - startRow][totalCols - startCol];

            ci = 0;

            for (int i = startRow; i < totalRows; i++, ci++) {
                cj = 0;
                for (int j = startCol; j < totalCols; j++, cj++) {
                    tabArray[ci][cj] = getCellData(i, j);
                    System.out.println(tabArray[ci][cj]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }

        return tabArray;
    }

    public static String getCellData(int RowNum, int ColNum) throws Exception {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            if (Cell == null) {
                return "";
            }
            switch (Cell.getCellType()) {
                case STRING:
                    return Cell.getStringCellValue();
                case NUMERIC:
                    // Format the number to avoid scientific notation
                    DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(20); // Adjust based on the precision you need
                    return df.format(Cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(Cell.getBooleanCellValue());
                case FORMULA:
                    return Cell.getCellFormula();
                default:
                    return "";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    //Used to retrieve Specific Excel Data
    public static String GetData(String sheetName, String tableHeader) {
        String cellValue = "";
        FileInputStream file = null;
        Workbook workbook = null;

        try {
            ConfigProperties.initializePropertyFile();
            file = new FileInputStream(ConfigProperties.property.getProperty("TestData"));
            workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.out.println("Sheet not found: " + sheetName);
                return null;
            }

            // Find the header row (assumed to be the first row)
            Row headerRow = sheet.getRow(0);
            int headerIndex = -1;

            // Find the column index of the provided header
            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                if (headerRow.getCell(i).getStringCellValue().equalsIgnoreCase(tableHeader)) {
                    headerIndex = i;
                    break;
                }
            }

            if (headerIndex == -1) {
                System.out.println("Header not found: " + tableHeader);
                return null;
            }

            // Get the data from the second row (row index 1)
            Row dataRow = sheet.getRow(1);
            if (dataRow != null) {
                Cell cell = dataRow.getCell(headerIndex);
                if (cell != null) {
                    cellValue = cell.toString();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) workbook.close();
                if (file != null) file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cellValue;
    }


    public static Map<String, String> RetrieveTestData(String filePath, int testId) {
        Map<String, String> testData = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Open the specific sheet
            Sheet sheet = workbook.getSheet("Test Funzionali");
            if (sheet == null) {
                throw new RuntimeException("Sheet 'Test Funzionali' not found in the Excel file.");
            }

            // Identify the header row and find the indices for the required columns
            Row headerRow = sheet.getRow(2);
            int obiettivoColIndex = -1;
            int azioneColIndex = -1;
            int risultatiAttesiColIndex = -1;

            for (Cell cell : headerRow) {
                String cellValue = cell.getStringCellValue();
                if (cellValue.equalsIgnoreCase("Obiettivo")) {
                    obiettivoColIndex = cell.getColumnIndex();
                } else if (cellValue.equalsIgnoreCase("Azione")) {
                    azioneColIndex = cell.getColumnIndex();
                } else if (cellValue.equalsIgnoreCase("Risultati Attesi")) {
                    risultatiAttesiColIndex = cell.getColumnIndex();
                }
            }

            if (obiettivoColIndex == -1 || azioneColIndex == -1 || risultatiAttesiColIndex == -1) {
                throw new RuntimeException("One or more required columns were not found in the Excel sheet.");
            }

            // Loop through rows to find the matching N.ID and retrieve data
            for (Row row : sheet) {
                Cell idCell = row.getCell(0); // N.ID is in the first column

                if (idCell != null && idCell.getCellType() == CellType.NUMERIC && (int) idCell.getNumericCellValue() == testId) {
                    testData.put("TestName", testId + " " + row.getCell(obiettivoColIndex).getStringCellValue());
                    testData.put("TestDescription", row.getCell(azioneColIndex).getStringCellValue());
                    testData.put("ExpectedResults", row.getCell(risultatiAttesiColIndex).getStringCellValue());
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData;
    }


}