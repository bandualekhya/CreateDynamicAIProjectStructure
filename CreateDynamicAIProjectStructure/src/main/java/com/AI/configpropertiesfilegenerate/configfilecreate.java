package com.AI.configpropertiesfilegenerate;

import java.nio.file.Files;
import java.nio.file.Path;

public class configfilecreate {

    public static void generateTxtFile(String projectName,String fileName) throws Exception {
        try {

            Path batFilePath = Path.of(projectName, fileName+".txt"); // Define the .yml file path directly in the projectName folder

            if (Files.notExists(batFilePath)) {
                Files.createFile(batFilePath); // Create the docker-compose.yml file if it doesn't exist
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
