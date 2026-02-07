package com.AI.ReadDataFromExcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReadData {

    public static String DynamicallyReadExcelFile() throws Exception {
        // Get the user directory
        String userDirPath = System.getProperty("user.dir") + File.separator;
        System.out.println("User Directory: " + userDirPath);
        // Set the ImportData directory path
        String importDataDirPath = userDirPath + "InputDataForNewAutomationStructure" + File.separator;
        File importDataDir = new File(importDataDirPath);
        // Check if the ImportData directory exists
        if (!importDataDir.exists() || !importDataDir.isDirectory()) {
            throw new FileNotFoundException("ImportData directory does not exist: " + importDataDirPath);
        }
        // Dynamically find the first .xlsx file in the ImportData directory
        Optional<File> optionalFile = Arrays.stream(importDataDir.listFiles())
                .filter(file -> file.isFile() && file.getName().toLowerCase().endsWith(".xlsx"))
                .findFirst();
        // If no .xlsx file is found, throw an exception
        if (!optionalFile.isPresent()) {
            throw new FileNotFoundException("No .xlsx file found in the ImportData directory.");
        }
        File dataFile = optionalFile.get();
        System.out.println("Using file: " + dataFile.getName());
        // Return the filename of the processed Excel file
        return dataFile.getName();
    }

    public static List<List<String>> readExcelFile(String fileName, String sheetName) throws Exception {
        // Get the user directory
        String userDirPath = System.getProperty("user.dir") + File.separator;
        // Construct the file path
        String filePath = userDirPath + "InputDataForNewAutomationStructure" + File.separator + fileName;
        File dataFile = new File(filePath);
        // Check if the file exists
        if (!dataFile.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        List<List<String>> excelData = new ArrayList<>();
        // Open the Excel file and process it
        try (FileInputStream fileInputStream = new FileInputStream(dataFile);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            // Validate and get the sheet index
            int sheetIndex;
            if (isNumeric(sheetName)) {
                sheetIndex = Integer.parseInt(sheetName);
                if (sheetIndex < 0 || sheetIndex >= workbook.getNumberOfSheets()) {
                    throw new IllegalArgumentException("Sheet index out of range: " + sheetIndex);
                }
            } else {
                // Attempt to get the sheet by name instead
                Sheet sheetByName = workbook.getSheet(sheetName);
                if (sheetByName == null) {
                    throw new IllegalArgumentException("Sheet not found: " + sheetName);
                }
                sheetIndex = workbook.getSheetIndex(sheetByName);
            }
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int totalXLRows = sheet.getLastRowNum();
            System.out.println("Total Number of Rows in Excel is: " + totalXLRows);
            // Iterate through rows
            for (int rowIndex = 1; rowIndex <= totalXLRows; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                // Skip if the row is null
                if (row == null) {
                    System.out.println("Skipping null row at index: " + rowIndex);
                    continue;
                }
                // Read data from the row and store it in a list
                List<String> rowData = new ArrayList<>();

                //String cellValue = getCellData(row.getCell(0)).trim();
                String cellValue = getCellData(row.getCell(1)).trim();
                // Check if cellValue is not empty
               // if (!cellValue.isEmpty()&&!cellValue2.isEmpty()) {
                    if (!cellValue.isEmpty()) {
                    rowData.add(cellValue);
                    //rowData.add(cellValue2);
                }
                else {
                    rowData.add(cellValue); // Add cellValue as is if it already contains a protocol
                    //rowData.add(cellValue2);
                }
                // Add the row data to the main list if it contains any values
                if (!rowData.isEmpty()) {
                    excelData.add(rowData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return the list containing the Excel data
        return excelData;
    }
    public static String getCellData(Cell cell) {
        String cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
            }
        }
        return (cellValue == null ? "" : cellValue);
    }
    // Utility method to check if a string is numeric
    private static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
