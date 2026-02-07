package com.AI.modulegenerate;


import java.nio.file.Files;
import java.nio.file.Path;

import com.AI.foldergenerate.*;
import com.AI.javaclassgenerate.JavaClassCreate;

public class moduleCreate {
    public static foldercreate foldercreate = new foldercreate();
    public static JavaClassCreate javaClassCreate = new JavaClassCreate();

    //src  : This is the root directory for the source code of the project.
    //main : This subdirectory is meant for the main (production) source code. In Maven or Gradle, this distinguishes production code from test code, which would be in /src/test.
    //java : This directory is specifically for Java source files. It indicates that the code in this path is written in the Java programming language.
    /* /com/test/automation: This is the package structure for the Java files.
    It maps directly to the package declaration at the beginning of Java source files. Each subdirectory represents a level of the package hierarchy:
    */
    //com: Top-level package.
    //wheels: Subpackage under com.
    //automation: Subpackage under com.test.

    public static void generateCommonModule(String projectName, String projectCommonModule, String basePackageName, String featurePackageName
            , String specificPackageName,
                                            String commonbaseclassName,
                                            String commonclassName,
                                            String commonlocatorClassName,
                                            String commonpage1ClassName,
                                            String commonpage2ClassName
    ) throws Exception {
        try {
           // Files.createDirectories(Path.of(projectName + "/" + projectCommonModule + "/src/main"));
            Files.createDirectories(Path.of(projectName + "/" + projectCommonModule + "/src/test"));
            //Files.createDirectories(Path.of(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName));
            Files.createDirectories(Path.of(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + "/base"));
            foldercreate.createFileWithContent(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName +
                    "/" + featurePackageName + "/" + specificPackageName + "/base" + "/" + commonbaseclassName,".java",
                    javaClassCreate.generateClassSource("CommonBase", basePackageName, featurePackageName, specificPackageName));


            Files.createDirectories(Path.of(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + "/common"));
            foldercreate.createFileWithContent(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + "/common" + "/" + commonclassName,".java", javaClassCreate.generateClassSource("CommonBy", basePackageName, featurePackageName, specificPackageName));
            Files.createDirectories(Path.of(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + "/pagelocators"));
            foldercreate.createFileWithContent(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + "/pagelocators" + "/" + commonlocatorClassName,".java", javaClassCreate.generateClassSource("ProjectCommonLocator", basePackageName, featurePackageName, specificPackageName));
            Files.createDirectories(Path.of(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + "/pages"));
            foldercreate.createFileWithContent(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + "/pages" + "/" + commonpage1ClassName,".java", javaClassCreate.generateClassSource("ClientSelectionPage", basePackageName, featurePackageName, specificPackageName));
            foldercreate.createFileWithContent(projectName + "/" + projectCommonModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + "/pages" + "/" + commonpage2ClassName,".java", javaClassCreate.generateClassSource("LoginPage", basePackageName, featurePackageName, specificPackageName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateMainModule(String projectName, String projectMainModule, String basePackageName, String featurePackageName, String specificPackageName, String functionalPackageName) throws Exception {
        try {

            Files.createDirectories(Path.of(projectName + "/" + projectMainModule + "/src/test/resource"));
          //  Files.createDirectories(Path.of(projectName + "/" + projectMainModule + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + "/" + functionalPackageName));

            Files.createDirectories(Path.of(projectName+"/"+projectMainModule + "/src/main/java/" + basePackageName +"/"+featurePackageName+"/"+specificPackageName +"/"+functionalPackageName+"/base"));
            Files.createDirectories(Path.of(projectName+"/"+projectMainModule + "/src/main/java/" + basePackageName +"/"+featurePackageName+"/"+specificPackageName +"/"+functionalPackageName+"/common"));
            Files.createDirectories(Path.of(projectName+"/"+projectMainModule + "/src/main/java/" + basePackageName +"/"+featurePackageName+"/"+specificPackageName +"/"+functionalPackageName+"/constants"));
            Files.createDirectories(Path.of(projectName+"/"+projectMainModule + "/src/main/java/" + basePackageName +"/"+featurePackageName+"/"+specificPackageName +"/"+functionalPackageName+"/pagelocators"));
            Files.createDirectories(Path.of(projectName+"/"+projectMainModule + "/src/main/java/" + basePackageName +"/"+featurePackageName+"/"+specificPackageName +"/"+functionalPackageName+ "/pages"));
            Files.createDirectories(Path.of(projectName+"/"+projectMainModule + "/src/main/java/" + basePackageName +"/"+featurePackageName+"/"+specificPackageName +"/"+functionalPackageName+ "/test"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void generateSendEmailModule(String projectName, String EmailModuleName, String basePackageName, String featurePackageName
            , String specificPackageName,String functionalPackageName,
                                            String smokeclassName,
                                            String regressionclassName

    ) throws Exception {
        try {

            Files.createDirectories(Path.of(projectName + "/" + EmailModuleName + "/src/test"));
            //Files.createDirectories(Path.of(projectName + "/" + EmailModuleName + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName));
            Files.createDirectories(Path.of(projectName + "/" + EmailModuleName + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName + functionalPackageName+"/test"));
            foldercreate.createFileWithContent(projectName + "/" + EmailModuleName + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName +"/" + functionalPackageName  + regressionclassName,".java", javaClassCreate.generateClassSource(regressionclassName, basePackageName, featurePackageName, specificPackageName,functionalPackageName));
            foldercreate.createFileWithContent(projectName + "/" + EmailModuleName + "/src/main/java/" + basePackageName + "/" + featurePackageName + "/" + specificPackageName  +"/" + functionalPackageName + smokeclassName,".java", javaClassCreate.generateClassSource(smokeclassName, basePackageName, featurePackageName, specificPackageName,functionalPackageName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }













}
