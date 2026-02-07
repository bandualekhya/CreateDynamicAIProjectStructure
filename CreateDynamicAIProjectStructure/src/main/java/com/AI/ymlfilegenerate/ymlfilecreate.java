package com.AI.ymlfilegenerate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ymlfilecreate {

    public static void generateYMLFile(String projectName,String fileName) throws Exception {
        try {
            Path ymlFilePath = Path.of(projectName, fileName + ".yml"); // Define the .yml file path directly in the projectName folder

            if (Files.notExists(ymlFilePath)) {
                Files.createFile(ymlFilePath); // Create the docker-compose.yml file if it doesn't exist
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
