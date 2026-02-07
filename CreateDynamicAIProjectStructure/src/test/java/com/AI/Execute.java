package com.AI;


import com.AI.ReadDataFromExcel.ReadData;
import com.AI.batfilegenerate.*;
import com.AI.configpropertiesfilegenerate.*;
import com.AI.foldergenerate.*;
import com.AI.javaclassgenerate.*;
import com.AI.modulegenerate.*;
import com.AI.xmlfilegenerate.*;
import com.AI.ymlfilegenerate.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


public class Execute {
    public static xmlfilecreate xmlfilecreate = new xmlfilecreate();
    public static ymlfilecreate ymlfilecreate = new ymlfilecreate();
    public static batfilecreate batfilecreate = new batfilecreate();
    public static moduleCreate moduleCreate = new moduleCreate();
    public static foldercreate foldercreate = new foldercreate();
    public static configfilecreate configfilecreate = new configfilecreate();
    public static ReadData readData = new ReadData();

    public static void main(String[] args) throws Exception {
        List<List<String>> excelData = null;


        String projectModule = "qlik";
        String MacroFolderName = "macro";
        String testDataFolderName = "testData";
        String webdriverFolderName = "webdriver";
        String ymlfileName = "docker-compose";
        String regbatfileName = "run-regression";
        String smokebatfileName = "run-smoke";
        String configfileName = "config.properties";

        String fileName = readData.DynamicallyReadExcelFile();
        System.out.println("File Name: " + fileName);
        // Read data from the specified Excel file
        excelData = readData.readExcelFile(fileName, "0");

        try {

            String projectName = excelData.get(0).get(0);  // Get the first element from the first row

            String projectCommonModule = excelData.get(1).get(0);

            String CommonModulePackages = excelData.get(2).get(0);
            String CommonModulePackage[] = CommonModulePackages.split(",");
            // Define an array to hold the package names dynamically
            String[] packageNames = new String[CommonModulePackage.length];
            // Loop through the CommonModule array and assign values dynamically
            for (int i = 0; i < CommonModulePackage.length; i++) {
                packageNames[i] = CommonModulePackage[i];  // Store each value in the classNames array
            }

            //SubCommonModules
            String SubCommonModules = excelData.get(3).get(0);
            String SubCommonModule[] = SubCommonModules.split(",");
            // Define an array to hold the class names dynamically
            String[] commonModule = new String[SubCommonModule.length];
            // Loop through the SubCommonModule array and assign values dynamically
            for (int i = 0; i < SubCommonModule.length; i++) {
                commonModule[i] = SubCommonModule[i];  // Store each value in the classNames array
            }
            //CommonModuleclasses
            String CommonModules = excelData.get(4).get(0);
            String CommonModule[] = CommonModules.split(",");
            // Define an array to hold the class names dynamically
            String[] commonModuleclass = new String[CommonModule.length];
            // Loop through the SubCommonModule array and assign values dynamically
            for (int i = 0; i < CommonModule.length; i++) {
                commonModuleclass[i] = CommonModule[i];  // Store each value in the classNames array
            }



            List<String> projectModules = List.of(projectCommonModule, "test", "sendemail");

            Path outputDir = Path.of("OutputRegenerationAutomationStructure", projectName);

            // Create the main project root
            Files.createDirectories(Path.of(String.valueOf(outputDir)));

            // Create the common module root
            moduleCreate.generateCommonModule(String.valueOf(outputDir),projectCommonModule,CommonModulePackage[0],
                    CommonModulePackage[1],CommonModulePackage[2],commonModuleclass[0],commonModuleclass[1],commonModuleclass[2],commonModuleclass[3],commonModuleclass[4]);

            // Create the common module pom file
            xmlfilecreate.generateCommonPomFile(String.valueOf(outputDir), projectCommonModule, "com", "wheels", "automation");

            // Create the send email module root
            moduleCreate.generateSendEmailModule(String.valueOf(outputDir), "send-email", "com",
                    "wheels", "automation", "test","SendEMail_Smoke","SendEMail_Regression");

            // Create the send email module pom file
            xmlfilecreate.generateModulePomFile(String.valueOf(outputDir), "send-email", "com", "wheels", "automation",
                    projectModule, "test");

            //for (String module : projectModules) {


            // Create the main module root
            moduleCreate.generateMainModule(String.valueOf(outputDir), projectModule, "com",
                    "wheels", "automation", "qlikcontent");
            xmlfilecreate.generateModulePomFile(String.valueOf(outputDir), projectCommonModule, "com", "wheels", "automation",
                    projectModule, "qlikcontent");

            //  }


            xmlfilecreate.getTestNGXmlContent(String.valueOf(outputDir), projectModule, "login");
            xmlfilecreate.generateMainPomFile(String.valueOf(outputDir), "1.40", "com", "wheels", "automation", projectModules);
            ymlfilecreate.generateYMLFile(String.valueOf(outputDir), ymlfileName);
            batfilecreate.generateBatFile(String.valueOf(outputDir), regbatfileName);
            batfilecreate.generateBatFile(String.valueOf(outputDir), smokebatfileName);
            configfilecreate.generateTxtFile(String.valueOf(outputDir), configfileName);
            foldercreate.testDataFolder(String.valueOf(outputDir), testDataFolderName);
            foldercreate.MacroFolder(String.valueOf(outputDir), MacroFolderName);
            foldercreate.webdriverFolder(String.valueOf(outputDir), webdriverFolderName);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
