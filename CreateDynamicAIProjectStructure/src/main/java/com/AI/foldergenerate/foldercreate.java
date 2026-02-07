package com.AI.foldergenerate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class foldercreate {

    // Method to create a directory
    public static void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // Create the testData folder
    public static void testDataFolder(String projectName,String testDataFolderName) throws Exception {
        try {
            Files.createDirectories(Path.of(projectName + "/" + testDataFolderName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Create the macro folder
    public static void MacroFolder(String projectName,String MacroFolderName) throws Exception {
        try {
            Files.createDirectories(Path.of(projectName + "/" + MacroFolderName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create the webdriver folder
    public static void webdriverFolder(String projectName,String webdriverFolderName) throws Exception {
        try {
            Files.createDirectories(Path.of(projectName + "/" + webdriverFolderName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to create a file with content
    public static void createFileWithContent(String path, String fileformat, String content) throws IOException {
        path = path+fileformat;
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

}
