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

## TODO

* TODO: Move ibisdoc.xsd to a common location.
* TODO: In Frank2Maven, Frank2MultiConfig is not properly referenced.
* TODO: In Frank2Maven's README, explain CI/CD.
* TODO: In Frank2Maven's README, explain that custom code is always common to all configs.
