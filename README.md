# frank-maven-examples

This project explains how to embed Frank configurations in a CI/CD pipeline while maintaining compatibility with the Frank!Runner. CI/CD becomes easier when you use Maven to build your project. Maven requires you to use a specific directory structure for your files. The examples within this project show you how to structure your files in such a way that both Maven and the Frank!Runner can work with them. You also learn how to integrate the Frank!Runner with Visual Studio Code.

There are three subdirectories: Frank2Maven, Frank2MultiConfig and Frank2MultiConfig_config. Frank2Maven shows how to have your Frank in a Maven project. This example has custom Java code, demonstrating how custom Java code can be included in your Frank.

Frank2MultiConfig and Frank2MultiConfig_config belong together. This example demonstrates how to have independent Frank configs. Each of these configs can be hosted in its own git repository, and it can follow its own DTAP (development-test-acceptance-deployment) cycle. This flexibility has a price however because this setup is much more complicated. For small projects with custom code, the Frank2Maven approach is preferred.

In real life, the three subdirectories should be independent GitHub repositories. This is not a real project however but a set of examples. It would be confusing to distribute a single example, Frank2MultiConfig and Frank2MultiConfig_config over two independent git projects, because they have to be stored on GitHub. GitHub does not allow nested organizations, making it impossible to group the projects. Within this education context, it is best to have one Git project for the three subdirectories, but please remember that in a real project you would make three independent git repositories.

## Installation

Please do the following to work with the projects inside this git repository:

* Clone this git repository to your development computer.
* Enter the root directory of your checkout.
* Clone the Frank!Runner. Your directory structure should be:

      frank-maven-examples
      |- frank-runner (in .gitignore of frank-maven-examples, so no nested git repositories)
      |- Frank2Maven
      |- Frank2MultiConfig
      |- Frank2MultiConfig_config

* Put ibisdoc.xsd in every `src/main/java/configurations` and `src/main/java/configuration` folder. This way you will have XML schema validation when you open configuration files in your text editor.

## Background

The Frank!Manual makes a clear distinction between Franks and Frank configs, see https://frank-manual.readthedocs.io/en/latest/gettingStarted/examineExample.html. The combination of all configuration files provided to the Frank!Runner is a Frank. Frank developers create Frank configs, which are independent of the Frank!Framework instance to which they are deployed.

But... subproject Frank2Maven holds a Frank! This may be confusing. The reason has to do with the history of the Frank!Framework. The Frank!Framework started as a Java project, making no distinction between the Frank!Framework sources and Frank configs. When you developed, you typically compiled the Java sources while deploying your Frank.

Compiling Java sources during the development process required a complicated development environment. Therefore, WeAreFrank! has separated the Frank!Framework from the Frank configs being deployed on it. The Frank!Framework executable appears as a web application that can be deployed on an application server. WeAreFrank! also introduced the Frank!Runner, see https://github.com/ibissource/frank-runner. This project automates the following steps:

* Download Apache Tomcat.
* Download the Frank!Framework web application.
* Deploy the web application while including the Frank configs being developed.

The Frank!Runner expresses the distinction between the Frank!Framework and Frank configs by introducing the `classes`, `configurations` and `tests` directories. These directories are explained in the README.md file of the Frank!Runner, see https://github.com/ibissource/frank-runner. This directory structure is also explained in the Frank!Manual. Frank developers create Frank configs, which are deployed as subdirectories of the `configurations` directory.

However, many existing Franks do not use the new directory structure. They are Java projects that use Maven. The words "Java" and "Maven" require more explanation. Java is a programming language that reads text files with extensions ".java". These text files need to be compiled using the Java compiler called "javac". The Java compiler reads the ".java" files and produces binary files with extension ".class". These Java class files can be executed by your application server. The Frank!Framework can work with multiple application servers, but when you use the Frank!Runner you always use Apache Tomcat as your application server.

For small projects, it is feasible to call the Java compiler directly to get your ".class" files, but for large projects this would be very complicated. The Java compiler has to be called with many arguments. Maven is a tool that automates the compilation of Java projects. A Maven project should have an XML file called "pom.xml" that provides configuration settings about your development project. Maven projects have a fixed directory structure; only deviations from this fixed directory structure have to be configured in pom.xml.

This history explains that there are three ways to run Frank configurations. First, Frank projects can be maintained in Eclipse projects which have the source code of the Frank!Framework as a dependency. This way, your development environment needs to compile the Java sources of the Frank!Framework. This way of developing Frank configs is deprecated, because it is difficult to set up Eclipse this way.

The second method is using the Frank!Runner. This works well for development, but the Frank!Runner was not developed for deployment on production. The third way is to compile your Frank development project with a build tool, typically Maven. This way, you can embed your development project in a CI/CD pipeline. The artifacts resulting from this CI/CD pipeline can then be deployed on the production environment.

You can work easily with your development project if it can be handled both by the Frank!Runner and by Maven. Both the Frank!Runner and Maven have requirements on the directory structure. Therefore, only a few ways of organizing your files are supported. These possibilities are examined in the sub projects of this project. For each way, you learn whether your project holds a Frank config or a Frank. You also learn how to run your work with the Frank!Runner during development. And you learn how to configure Maven to build the artifact you need. Finally, for each way the pros and cons are examined.

## TODO

* TODO: Move ibisdoc.xsd to a common location.
* TODO: In Frank2Maven, Frank2MultiConfig is not properly referenced.
* TODO: In Frank2Maven's README, explain CI/CD.
* TODO: In Frank2Maven's README, explain that custom code is always common to all configs.
