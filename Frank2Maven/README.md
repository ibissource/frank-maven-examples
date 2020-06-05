# Frank with custom code and VS Code integration

This project (remember that it would be an independent Git repository in a real development project) demonstrates two features of the Frank!Runner. First, you see how you can add custom Java code to your project. Second, you see integration with Visual Studio Code, allowing you to start the Frank!Runner from this development environment.

## A Frank, not a config

The Frank!Manual makes a clear distinction between Franks and Frank configs, see https://frank-manual.readthedocs.io/en/latest/gettingStarted/examineExample.html. The combination of all configuration files provided to the Frank!Runner is a Frank. Frank developers create Frank configs, which are independent of the Frank!Framework instance to which they are deployed.

But... This project is not a Frank!Config, but a Frank! This may be confusing. The reason has to do with the history of the Frank!Framework. The Frank!Framework started as a Java project, making no distinction between the Frank!Framework sources and Frank configs. When you developed, you typically compiled the Java sources while deploying your Frank.

Compiling Java sources during the development process required a complicated development environment. Therefore, WeAreFrank! introduced the Frank!Runner, see https://github.com/ibissource/frank-runner. This project automates downloading Apache Tomcat and deploying the Frank!Framework, introducing a clear distinction between a Frank and a Frank config.

The Frank!Runner expresses this distinction by introducing the `classes`, `configurations` and `tests` directories as explained in the README.md file of the Frank!Runner, https://github.com/ibissource/frank-runner. This directory structure is also explained in the Frank!Manual. Frank developers create Frank configs, which are deployed as subdirectories of the `configurations` directory.

However, many existing Franks do not use the new directory structure. They are Java projects that use Maven. The words "Java" and "Maven" require more explanation. Java is a programming language that reads text files with extensions ".java". These text files need to be compiled using the Java compiler called "javac". The Java compiler reads the ".java" files and produces binary files with extension ".class". These Java class files can be executed by your application server. The Frank!Framework can work with multiple application servers, but when you use the Frank!Runner you always use Apache Tomcat as your application server.

For small projects, it is feasible to call the Java compiler directly to get your ".class" files, but for large projects this would be very complicated. The Java compiler has to be called with many arguments. Maven is a tool that automates the compilation of Java projects. A Maven project should have an XML file called "pom.xml" that provides configuration settings about your development project. Maven projects have a fixed directory structure; only deviations from this fixed directory structure have to be configured in pom.xml. This fixed directory structure is not compatible with the directory structure explained in the Frank!Manual. With the Maven directory structure, you usually loose the distinction between a Frank and a Frank config. Therefore a Maven project contains a Frank, not a Frank config.

Please note that WeAreFrank! developed a more complicated project structure in which custom code is possible AND in which you have one Frank config per project. This structure is beyond the scope of the present project, but you can find more information in the README.md file of the Frank!Runner, https://github.com/ibissource/frank-runner.

The Frank!Runner checks whether your project contains a file named `pom.xml` at your project root. If so, your project is recognized as a Maven project.

## Directory structure and VSCode integration

This project demonstrates the directory structure of a Maven project with Frank configs. Of course, you have a file pom.xml. Maven projects have Java source code in directory "src/main/java". Maven projects also have unit tests, which are Java classes that perform tests on parts of the production Java code. These tests are in "src/test/java".

The Frank!Runner expects your Frank configs in directory "src/main/configurations", each Frank config appearing as a subdirectory. More details can be found in the README.md file of the Frank!Runner GitHub repository: https://github.com/ibissource/frank-runner. That README.md file also explains how to get integration with Visual Studio Code. This integration is achieved using the file build.xml within the present project.

## What the Frank!Runner does

The Frank!Runner was designed to simplify executing Franks. This has been achieved by limiting the number of tools being used under the hood. The Frank!Runner therefore does not use Maven to compile your Java sources. Instead, the Frank!Runner uses another tool that can be used to automate the build, which is Apache Ant.

The Frank!Runner does the following:

* Download Apache Tomcat.
* Download the compiled Frank!Framework (file with extension .war).
* Grab some dependencies of all Java code being deployed.
* Compile your Java sources, automating this compilation process with Ant instead of Maven.
* Start Apache Tomcat, running all the Java code as a web application.

It is important that the Frank!Runner does not use Maven to build your Java code. This means that the Frank!Runner does not use the configuration settings you made in the pom.xml file. Furthermore, the Frank!Runner does not execute the unit tests you have in "src/test/java".

To run your unit tests, you may want to build your project using Maven. Installing and using Maven is beyond the scope of this README.md file. If you have Maven installed on your computer, you can do the following:

* Change directory to the project root of this project.
* On the command-line, execute `mvn install`.
