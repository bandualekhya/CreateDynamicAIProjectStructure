package com.AI.xmlfilegenerate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class xmlfilecreate {


    public static void generateCommonPomFile(String projectName, String projectCommonModule) throws IOException {
        // Define the path for the pom.xml file
        Path pomFilePath = Path.of(projectName + "/" + projectCommonModule + "/pom.xml");
        System.out.println("Generating POM at path: " + pomFilePath.toString());
        // Create the pom.xml file content with placeholders
        String pomContent = """
                <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                    <modelVersion>4.0.0</modelVersion>
                    <artifactId>common</artifactId>
                    <name>%s</name>
                    <parent>
                        <groupId>com.test.automation</groupId>
                        <artifactId>Qliksense</artifactId>
                        <version>1.0</version>
                    </parent>
                    <properties>
                        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                    </properties>
                    <build>
                        <finalName>${project.artifactId}-${project.version}</finalName>
                    </build>
                </project>
                """;
        // Inject the projectName into the pomContent
        pomContent = String.format(pomContent, projectName);
        // Write content to the file
        Files.createDirectories(pomFilePath.getParent()); // Ensure parent directories exist
        Files.write(pomFilePath, pomContent.getBytes(StandardCharsets.UTF_8));
    }

    public static void generateCommonPomFile(String projectName, String projectCommonModule,String basePackageName, String featurePackageName,
                                             String specificPackageName) throws IOException {


        // Extract only the name of the project without path separators
        String cleanProjectName = Path.of(projectName).getFileName().toString();

        // Define the path for the pom.xml file
        Path pomFilePath = Path.of(projectName + "/" + projectCommonModule + "/pom.xml");
        System.out.println("Generating POM at path: " + pomFilePath.toString());
        // Create the pom.xml file content with placeholders
        String pomContent = """
        <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://www.apache.org/xsd/maven-4.0.0.xsd">
            <modelVersion>4.0.0</modelVersion>
            <groupId>%s.%s.%s</groupId>
            <artifactId>%s</artifactId>
            <name>%s</name>
            <parent>
                <groupId>%s.%s.%s</groupId>
                <artifactId>%s</artifactId>
                <version>1.0</version>
            </parent>
            <properties>
                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            </properties>
            <build>
                <finalName>${project.artifactId}-${project.version}</finalName>
            </build>
        </project>
        """;
        // Format the content with actual values
        pomContent = String.format(
                pomContent,
                basePackageName,              // groupId base
                featurePackageName,           // feature group
                specificPackageName,          // specific group
                projectCommonModule,          // artifactId
                projectCommonModule,          // name
                basePackageName,              // parent groupId base
                featurePackageName,           // parent feature group
                specificPackageName,          // parent specific group
                cleanProjectName              // parent artifactId, cleaned of path separators

        );
        // Write content to the file
        Files.createDirectories(pomFilePath.getParent()); // Ensure parent directories exist
        Files.write(pomFilePath, pomContent.getBytes(StandardCharsets.UTF_8));
    }

    public static void generateModulePomFile(String projectName, String projectCommonModule,String basePackageName, String featurePackageName,
                                             String specificPackageName,String projectModule,String functionalPackageName) throws IOException {
        // Extract only the name of the project without path separators
        String cleanProjectName = Path.of(projectName).getFileName().toString();
        // Define the path for the pom.xml file
        Path pomFilePath = Path.of(projectName + "/" + projectModule + "/pom.xml");
        // Create the pom.xml file content with placeholders
        String pomContent = """
                <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://www.apache.org/xsd/maven-4.0.0.xsd">
                    <modelVersion>4.0.0</modelVersion>
                    <artifactId>%s</artifactId>
                    <name>%s</name>

                    <parent>
                        <groupId>%s.%s.%s</groupId>
                        <artifactId>%s</artifactId>
                        <version>1.0</version>
                    </parent>

                    <dependencies>
                           <!-- automation commons jar file-->
                           <dependency>
                               <groupId>%s.%s.%s</groupId>
                               <artifactId>%s</artifactId>
                               <version>1.0</version>
                           </dependency>
                       </dependencies>

                    <properties>
                        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                    </properties>

                    <build>
                        <finalName>${project.artifactId}-${project.version}</finalName>
                    </build>
                </project>
                """;
        // Inject dynamic values into the pomContent
        // Format the content with actual values
        pomContent = String.format(
                pomContent,
                functionalPackageName,        // artifactId
                functionalPackageName,        // name
                basePackageName,              // groupId base
                featurePackageName,           // feature group
                specificPackageName,          // specific group
                cleanProjectName,                  // artifactId
                basePackageName,              // parent groupId base
                featurePackageName,           // parent feature group
                specificPackageName,          // parent specific group
                projectCommonModule           // artifactId
        );
        // Write content to the file
        Files.createDirectories(pomFilePath.getParent()); // Ensure parent directories exist
        Files.write(pomFilePath, pomContent.getBytes(StandardCharsets.UTF_8));
    }




    public static void generateMainPomFile(String projectName, String version, String basePackageName, String featurePackageName,
                                           String specificPackageName, List<String> projectModules) throws IOException {
        // Extract only the name of the project without path separators
        String cleanProjectName = Path.of(projectName).getFileName().toString();
        // Define the path for the pom.xml file
        Path pomFilePath = Path.of(projectName + "/pom.xml");
        // Create the <modules> section dynamically from the list
        StringBuilder modulesSection = new StringBuilder();
        for (String module : projectModules) {
            modulesSection.append("        <module>").append(module).append("</module>\n");
        }
        // Create the pom.xml file content with placeholders
        String pomContent = """
                <project xmlns="http://maven.apache.org/POM/4.0.0"
                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                    <modelVersion>4.0.0</modelVersion>
                                
                    <groupId>%s.%s.%s</groupId>
                    <artifactId>%s</artifactId>
                    <version>1.0</version>
                    <packaging>pom</packaging>
                    <name>${project.artifactId}</name>
                                
                     <modules>
                           %s
                     </modules>
                                
                    <properties>
                        <jboss-transaction-api.version>1.0.1</jboss-transaction-api.version>
                        <javax.persistence-api.version>2.2</javax.persistence-api.version>
                        <automation.commontest.regex>common</automation.commontest.regex>
                        <hibernate-core.version>5.3.3.Final</hibernate-core.version>
                        <maven-compiler.version>3.8.0</maven-compiler.version>
                        <extentreports.version>3.1.5</extentreports.version>
                        <jboss-logging.version>3.3.2</jboss-logging.version>
                        <hibernate-jpa.version>1.0.2</hibernate-jpa.version>
                        <build.sourceEncoding>UTF-8</build.sourceEncoding>
                        <commons-lang.version>3.0</commons-lang.version>
                        <freemarker.version>2.3.28</freemarker.version>
                        <byte-buddy.version>1.8.0</byte-buddy.version>
                        <classmate.version>1.3.0</classmate.version>
                        <commons-io.version>2.5</commons-io.version>
                        <xmlbeans.version>2.6.0</xmlbeans.version>
                        <mongodb.version>3.7.0</mongodb.version>
                        <testng.version>7.8.0</testng.version>
                        <hsqldb.version>2.4.0</hsqldb.version>
                        <mysql.version>6.0.6</mysql.version>
                        <dom4j.version>1.6.1</dom4j.version>
                        <antlr.version>2.7.7</antlr.version>
                        <guava.version>32.1.2-jre</guava.version>
                        <jtds.version>1.3.1</jtds.version>
                        <guice.version>4.0</guice.version>
                        <poi.version>3.13</poi.version>
                        <itextpdf.version>5.5.13</itextpdf.version>
                        <lombok.version>1.18.28</lombok.version>
                        <log4j.version>1.2.17</log4j.version>
                		<ngwebdriver.version>1.1.4</ngwebdriver.version>
                		<surefire.version>3.0.0-M4</surefire.version>
                		<webdrivermanager.version>5.4.1</webdrivermanager.version>
                        <!-- selenium version -->
                        <selenium.version>4.10.0</selenium.version>
                        <seleniumserver.version>3.141.59</seleniumserver.version>
                        <seleniumStandalone.version>2.53.0</seleniumStandalone.version>
                        <maven.compiler.source>17</maven.compiler.source>
                        <maven.compiler.target>17</maven.compiler.target>
                    </properties>
                                
                    <dependencies>
                        <dependency>
                            <groupId>%s.%s.%s</groupId>
                            <artifactId>automation-core</artifactId>
                            <version>%s</version>
                        </dependency>
                        <!-- extentreport -->
                        <dependency>
                            <groupId>com.aventstack</groupId>
                            <artifactId>extentreports</artifactId>
                            <version>${extentreports.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>com.beust</groupId>
                            <artifactId>jcommander</artifactId>
                            <version>1.82</version>
                        </dependency>
                        <dependency>
                            <groupId>org.freemarker</groupId>
                            <artifactId>freemarker</artifactId>
                            <version>${freemarker.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.mongodb</groupId>
                            <artifactId>mongodb-driver</artifactId>
                            <version>${mongodb.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.mongodb</groupId>
                            <artifactId>mongodb-driver-core</artifactId>
                            <version>${mongodb.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.mongodb</groupId>
                            <artifactId>bson</artifactId>
                            <version>${mongodb.version}</version>
                        </dependency>                               
                        <!-- AutoIT -->
                        <!-- https://mvnrepository.com/artifact/de.openkeyword/autoit -->
                        <dependency>
                            <groupId>de.openkeyword</groupId>
                            <artifactId>autoit</artifactId>
                            <version>0.0.15</version>
                        </dependency>
                        <dependency>
                            <groupId>net.sf.jacob-project</groupId>
                            <artifactId>jacob</artifactId>
                            <version>1.14.3</version>
                        </dependency>
                        <!-- commons-io -->
                        <dependency>
                            <groupId>commons-io</groupId>
                            <artifactId>commons-io</artifactId>
                            <version>${commons-io.version}</version>
                        </dependency>
                        <!-- commons -->
                        <dependency>
                            <groupId>org.apache.commons</groupId>
                            <artifactId>commons-lang3</artifactId>
                            <version>${commons-lang.version}</version>
                        </dependency>
                        <!-- guava -->
                        <dependency>
                            <groupId>com.google.guava</groupId>
                            <artifactId>guava</artifactId>
                            <version>${guava.version}</version>
                        </dependency>
                        <!-- guice -->
                        <dependency>
                            <groupId>com.google.inject</groupId>
                            <artifactId>guice</artifactId>
                            <version>${guice.version}</version>
                        </dependency>
                        <!-- Hibernate jars -->
                        <dependency>
                            <groupId>org.hibernate</groupId>
                            <artifactId>hibernate-core</artifactId>
                            <version>${hibernate-core.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.hibernate</groupId>
                            <artifactId>hibernate-commons-annotations</artifactId>
                            <version>3.2.0.Final</version>
                        </dependency>
                        <dependency>
                            <groupId>org.hibernate.javax.persistence</groupId>
                            <artifactId>hibernate-jpa-2.1-api</artifactId>
                            <version>1.0.2</version>
                        </dependency>
                        <dependency>
                            <groupId>org.jboss.logging</groupId>
                            <artifactId>jboss-logging</artifactId>
                            <version>3.3.2.Final</version>
                        </dependency>
                        <dependency>
                            <groupId>org.jboss.spec.javax.transaction</groupId>
                            <artifactId>jboss-transaction-api_1.2_spec</artifactId>
                            <version>1.1.1.Final</version>
                        </dependency>
                        <dependency>
                            <groupId>net.bytebuddy</groupId>
                            <artifactId>byte-buddy</artifactId>
                            <version>1.8.0</version>
                        </dependency>
                        <dependency>
                            <groupId>dom4j</groupId>
                            <artifactId>dom4j</artifactId>
                            <version>1.6.1</version>
                        </dependency>
                        <dependency>
                            <groupId>com.fasterxml</groupId>
                            <artifactId>classmate</artifactId>
                            <version>1.3.0</version>
                        </dependency>
                        <dependency>
                            <groupId>javax.persistence</groupId>
                            <artifactId>javax.persistence-api</artifactId>
                            <version>2.2</version>
                        </dependency>
                        <dependency>
                            <groupId>antlr</groupId>
                            <artifactId>antlr</artifactId>
                            <version>2.7.7</version>
                        </dependency>
                        <!-- hsqldb -->
                        <dependency>
                            <groupId>org.hsqldb</groupId>
                            <artifactId>hsqldb</artifactId>
                            <version>2.4.0</version>
                        </dependency>
                        <!-- poi -->
                        <dependency>
                            <groupId>org.apache.poi</groupId>
                            <artifactId>poi</artifactId>
                            <version>${poi.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.apache.poi</groupId>
                            <artifactId>poi-ooxml</artifactId>
                            <version>${poi.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.apache.poi</groupId>
                            <artifactId>poi-ooxml-schemas</artifactId>
                            <version>${poi.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>xml-apis</groupId>
                            <artifactId>xml-apis</artifactId>
                            <version>1.4.01</version>
                        </dependency>                                
                        <!-- selenium -->
                        <dependency>
                            <groupId>org.seleniumhq.selenium</groupId>
                            <artifactId>selenium-api</artifactId>
                            <version>${selenium.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.seleniumhq.selenium</groupId>
                            <artifactId>selenium-chrome-driver</artifactId>
                            <version>${selenium.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.seleniumhq.selenium</groupId>
                            <artifactId>selenium-ie-driver</artifactId>
                            <version>${selenium.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.seleniumhq.selenium</groupId>
                            <artifactId>selenium-firefox-driver</artifactId>
                            <version>${selenium.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.seleniumhq.selenium</groupId>
                            <artifactId>selenium-java</artifactId>
                            <version>${selenium.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.seleniumhq.selenium</groupId>
                            <artifactId>selenium-remote-driver</artifactId>
                            <version>${selenium.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.seleniumhq.selenium</groupId>
                            <artifactId>selenium-server</artifactId>
                            <version>${seleniumserver.version}</version>
                        </dependency>
                        <!--<dependency>
                            <groupId>org.seleniumhq.selenium</groupId>
                            <artifactId>selenium-server-standalone</artifactId>
                            <version>2.53.0</version>
                        </dependency>-->
                        <dependency>
                            <groupId>org.seleniumhq.selenium</groupId>
                            <artifactId>selenium-support</artifactId>
                            <version>${selenium.version}</version>
                        </dependency>
                        <!-- testng -->
                        <dependency>
                            <groupId>org.testng</groupId>
                            <artifactId>testng</artifactId>
                            <version>${testng.version}</version>
                        </dependency>                               
                        <dependency>
                            <groupId>org.apache.xmlbeans</groupId>
                            <artifactId>xmlbeans</artifactId>
                            <version>${xmlbeans.version}</version>
                        </dependency> 
                        <dependency>
                            <groupId>mysql</groupId>
                            <artifactId>mysql-connector-java</artifactId>
                            <version>${mysql.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>net.sourceforge.jtds</groupId>
                            <artifactId>jtds</artifactId>
                            <version>${jtds.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>com.itextpdf</groupId>
                            <artifactId>itextpdf</artifactId>
                            <version>${itextpdf.version}</version>
                        </dependency>
                        <!-- lombok -->
                        <dependency>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                            <scope>provided</scope>
                        </dependency>
                        <!-- Java Mail -->
                        <dependency>
                            <groupId>com.sun.mail</groupId>
                            <artifactId>javax.mail</artifactId>
                            <version>1.5.1</version>
                        </dependency>
                        <dependency>
                            <groupId>javax.mail</groupId>
                            <artifactId>javax.mail-api</artifactId>
                            <version>1.6.2</version>
                        </dependency>
                        <dependency>
                            <groupId>javax.activation</groupId>
                            <artifactId>activation</artifactId>
                            <version>1.1.1</version>
                        </dependency>
                        <dependency>
                            <groupId>org.jsoup</groupId>
                            <artifactId>jsoup</artifactId>
                            <version>1.12.1</version>
                        </dependency>
                <!--		  <dependency>
                            <groupId>io.github.bonigarcia</groupId>
                            <artifactId>webdrivermanager</artifactId>
                            <version>${webdrivermanager.version}</version>
                              <exclusions>
                                  <exclusion>
                                      <groupId>com.google.guava</groupId>
                                      <artifactId>guava</artifactId>
                                  </exclusion>
                              </exclusions>
                        </dependency>-->
                        <!-- SLF4J Bridge -->
                        <!--<dependency>
                            <groupId>org.apache.logging.log4j</groupId>
                            <artifactId>log4j-slf4j-impl</artifactId>
                            <version>2.13.1</version>
                        </dependency>-->
                		<dependency>
                            <groupId>com.paulhammant</groupId>
                            <artifactId>ngwebdriver</artifactId>
                            <version>${ngwebdriver.version}</version>
                        </dependency>
                		<!-- Joda date and time -->
                        <dependency>
                            <groupId>joda-time</groupId>
                            <artifactId>joda-time</artifactId>
                            <version>2.10</version>
                        </dependency>
                		<dependency>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-surefire-plugin</artifactId>
                            <version>${surefire.version}</version>
                            <type>maven-plugin</type>
                        </dependency>
                        <dependency>
                            <groupId>org.apache.logging.log4j</groupId>
                            <artifactId>log4j-api</artifactId>
                            <version>2.20.0</version>
                        </dependency>
                        <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
                        <dependency>
                            <groupId>org.slf4j</groupId>
                            <artifactId>slf4j-api</artifactId>
                            <version>2.0.7</version>
                        </dependency>
                    </dependencies>
                    <build>
                        <finalName>${project.artifactId}</finalName>
                        <pluginManagement>
                            <plugins>
                                <plugin>
                                  <!--  <groupId>org.apache.maven.plugins</groupId>
                                    <artifactId>maven-compiler-plugin</artifactId>
                                    <configuration>
                                        <source>1.8</source>
                                        <target>1.8</target>
                                    </configuration>-->
                                    <!--Java17 updated-->
                                    <artifactId>maven-compiler-plugin</artifactId>
                                    <configuration>
                                        <source>${maven.compiler.source}</source>
                                        <target>${maven.compiler.target}</target>
                                    </configuration>
                                </plugin>
                            </plugins>
                        </pluginManagement>
                        <plugins>
                            <plugin>
                                <groupId>org.apache.maven.plugins</groupId>
                                <artifactId>maven-surefire-plugin</artifactId>
                                <version>${surefire.version}</version>
                                <configuration>
                                    <suiteXmlFiles>
                                        <suiteXmlFile>
                                            ${basedir}/src/test/resources/testng.xml
                                        </suiteXmlFile>
                                    </suiteXmlFiles>
                                </configuration>
                            </plugin>     
                        </plugins>
                    </build>
                                
                    <profiles>
                        <profile>
                            <id>smoke</id>
                            <build>
                                <plugins>
                                    <plugin>
                                        <groupId>org.apache.maven.plugins</groupId>
                                        <artifactId>maven-surefire-plugin</artifactId>
                                        <version>${surefire.version}</version>
                                        <configuration>
                                            <suiteXmlFiles>
                                                <suiteXmlFile>
                                                    ${basedir}/src/test/resources/smoke-testng.xml
                                                </suiteXmlFile>
                                            </suiteXmlFiles>
                                        </configuration>
                                    </plugin>
                                </plugins>
                            </build>
                        </profile>
                                
                        <profile>
                            <id>regression</id>
                            <build>
                                <plugins>
                                    <plugin>
                                        <groupId>org.apache.maven.plugins</groupId>
                                        <artifactId>maven-surefire-plugin</artifactId>
                                        <version>${surefire.version}</version>
                                        <configuration>
                                            <suiteXmlFiles>
                                                <suiteXmlFile>
                                                    ${basedir}/src/test/resources/regression-testng.xml
                                                </suiteXmlFile>
                                            </suiteXmlFiles>
                                        </configuration>
                                    </plugin>
                                </plugins>
                            </build>
                        </profile>
                                
                        <profile>
                            <id>reporting</id>
                            <build>
                                <plugins>
                                    <plugin>
                                        <groupId>org.apache.maven.plugins</groupId>
                                        <artifactId>maven-surefire-plugin</artifactId>
                                        <version>${surefire.version}</version>
                                        <configuration>
                                            <suiteXmlFiles>
                                                <suiteXmlFile>
                                                    ${basedir}/src/test/resources/reporting-testng.xml
                                                </suiteXmlFile>
                                            </suiteXmlFiles>
                                        </configuration>
                                    </plugin>
                                </plugins>
                            </build>
                        </profile>
                                
                        <profile>
                            <id>demo</id>
                            <build>
                                <plugins>
                                    <plugin>
                                        <groupId>org.apache.maven.plugins</groupId>
                                        <artifactId>maven-surefire-plugin</artifactId>
                                        <version>${surefire.version}</version>
                                        <configuration>
                                            <suiteXmlFiles>
                                                <suiteXmlFile>
                                                    ${basedir}/src/test/resources/demo-testng.xml
                                                </suiteXmlFile>
                                            </suiteXmlFiles>
                                        </configuration>
                                    </plugin>
                                </plugins>
                            </build>
                        </profile>
                    </profiles>        
                </project>
                """;
        // Inject dynamic values into the pomContent
        // Format the content with actual values
        pomContent = String.format(
                pomContent,
                basePackageName,              // groupId base
                featurePackageName,           // feature group
                specificPackageName,          // specific group
                cleanProjectName,                  // artifactId

                modulesSection.toString().trim(),

                basePackageName,              // groupId base
                featurePackageName,           // feature group
                specificPackageName,          // specific group
                version                       // version

        );
        // Write content to the file
        Files.createDirectories(pomFilePath.getParent()); // Ensure parent directories exist
        Files.write(pomFilePath, pomContent.getBytes(StandardCharsets.UTF_8));
    }


    public static String getTestNGXmlContent(String projectName, String projectMainModule, String className) {
        // Ensure the method creates a Path object for the testng.xml file
        Path testngPath = Path.of(projectName, projectMainModule, "src/test/resource", "testng.xml");

        // Generate the testng.xml content dynamically based on the input parameters
        String testngContent = String.format(
                """
                <!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
                <suite name="Suite">
                    <test name="Test">
                        <classes>
                            <class name="%s"/>
                        </classes>
                    </test>
                </suite>
                """,
                className
        );

        // Write the generated content to the testng.xml file
        try {
            Files.createDirectories(testngPath.getParent()); // Ensure the directory exists
            Files.writeString(testngPath, testngContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create or write to testng.xml file", e);
        }

        return testngContent;
    }


















    public static void writePomFile(String projectName, String projectCommonModule, String projectTestModule, String PackageName) throws IOException {
        // Define the path for the pom.xml file
        Path pomFilePath = Path.of(projectName + "/" + projectCommonModule + "/pom.xml");
        // Create the pom.xml file content with placeholders
        String pomContent = """
                <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://www.apache.org/xsd/maven-4.0.0.xsd">
                    <modelVersion>4.0.0</modelVersion>
                    <artifactId>%s</artifactId>
                    <name>%s</name>

                    <parent>
                        <groupId>%s.%s.%s</groupId>
                        <artifactId>%s</artifactId>
                        <version>1.0</version>
                    </parent>

                    <modules>
                        <module>%s</module>
                    </modules>

                    <properties>
                        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                    </properties>

                    <build>
                        <finalName>${project.artifactId}-${project.version}</finalName>
                    </build>
                </project>
                """;
        // Inject dynamic values into the pomContent
        pomContent = String.format(pomContent, projectCommonModule, projectCommonModule, PackageName, projectName, projectTestModule);
        // Write content to the file
        Files.createDirectories(pomFilePath.getParent()); // Ensure parent directories exist
        Files.write(pomFilePath, pomContent.getBytes(StandardCharsets.UTF_8));
    }







}











