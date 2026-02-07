package com.test.automation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CreateProject {

    public static final String FILE_SEPARATOR = File.separator;
    public static void main(String[] args) {
        String projectName = "Ecommerce";
        String[] modules = {"Common"};
//        String[] modules = {"Common","Payment",
//                "Orders",
//                "Products",
//                "Cart"
//                };
        List<String> packages = List.of("com.test.automation.Common");
//        List<String> packages = List.of("com.test.automation.Pages", "com.test.automation.PageLocators",
//                "com.test.automation.tests","com.test.automation.Common");
//        String userDirPath = System.getProperty("user.dir") + FILE_SEPARATOR;
//        System.out.println(userDirPath);
//        String parentFilePath = new File(userDirPath).getParent() + FILE_SEPARATOR;
//        System.out.println(parentFilePath);
        // Base project directory
        Path baseDir = Paths.get("output",projectName);

        try {
            // Create the main project directory
            Files.createDirectories(baseDir);

            // Create parent POM
            createParentPom(baseDir, modules);

            // Create module directories and POMs
            for (String module : modules) {
                Path moduleDir = baseDir.resovb
                lve(module);
                Files.createDirectories(moduleDir.resolve("src/main/java"));
                Files.createDirectories(moduleDir.resolve("src/main/resources"));
                Files.createDirectories(moduleDir.resolve("src/test/java"));

                // Create package directories within src/main/java for each package
                for (String packageName : packages) {
                    Path packagePath = moduleDir.resolve("src/main/java/" + packageName.replace(".", "/"));
                    Files.createDirectories(packagePath);
                }

                createModulePom(moduleDir, module);
            }

            System.out.println("Maven multi-module project structure created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create the parent POM file
    private static void createParentPom(Path baseDir, String[] modules) throws IOException {
        String parentPomContent = """
            <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                <groupId>com.test.automation</groupId>
                <artifactId>%s</artifactId>
                <version>1.0-SNAPSHOT</version>
                <packaging>pom</packaging>
                <modules>
                    %s
                </modules>
                <dependencies>
                    <!-- Define common dependencies here -->
                </dependencies>
             </project>
            """.formatted(baseDir.getFileName(), generateModulesXml(modules));

        Files.writeString(baseDir.resolve("pom.xml"), parentPomContent, StandardCharsets.UTF_8);
    }

    // Create module POM files
    private static void createModulePom(Path moduleDir, String moduleName) throws IOException {
        String modulePomContent = """
            <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                
                <parent>
                    <groupId>com.test.automation</groupId>
                    <artifactId>%s</artifactId>
                    <version>1.0-SNAPSHOT</version>
                    <relativePath>../pom.xml</relativePath>
                </parent>
                
                <artifactId>%s</artifactId>
                <version>1.0-SNAPSHOT</version>
                
                <dependencies>
                    <!-- Define module-specific dependencies here -->
                </dependencies>
                
            </project>
            """.formatted(moduleDir.getParent().getFileName(), moduleName);

        Files.writeString(moduleDir.resolve("pom.xml"), modulePomContent, StandardCharsets.UTF_8);
    }

    // Generate XML string for modules
    private static String generateModulesXml(String[] modules) {
        StringBuilder modulesXml = new StringBuilder();
        for (String module : modules) {
            modulesXml.append("<module>").append(module).append("</module>\n");
        }
        return modulesXml.toString();
    }

}
