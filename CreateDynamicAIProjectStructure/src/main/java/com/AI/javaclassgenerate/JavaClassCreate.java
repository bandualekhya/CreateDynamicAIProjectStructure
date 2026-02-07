package com.AI.javaclassgenerate;

public class JavaClassCreate {

    // Method to generate the source code for the dynamic class
    public static String generateClassSource(String className,String basePackageName,String featurePackageName,String specificPackageName ) {
        return
                "package "+basePackageName.trim()+"."+featurePackageName.trim()+"."+specificPackageName+ "\n"+



                "public class " + className + " {\n" +

                "}";
    }
    // Method to generate the source code for the dynamic class
    public static String generateClassSource2(String className, String methodName, String methodBody) {
        return "public class " + className + " {\n" +
                "    public void " + methodName + "() {\n" +
                "        " + methodBody + "\n" +
                "    }\n" +
                "}";
    }

    public static String generateClassSource(String className,String basePackageName,String featurePackageName,String specificPackageName,String functionalPackageName ) {
        return
                "package "+basePackageName.trim()+"."+featurePackageName.trim()+"."+specificPackageName.trim()+"."+functionalPackageName.trim()+ "\n"+



                        "public class " + className + " {\n" +

                        "}";
    }
}
