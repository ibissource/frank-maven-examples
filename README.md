This project explains how to embed Frank configurations in a CI/CD pipeline while maintaining compatibility with the Frank!Runner. CI/CD becomes easier when you use Maven to build your project. Maven requires you to use a specific directory structure for your files. The examples within this project show you how to structure your files in such a way that both Maven and the Frank!Runner can work with them.

There are three subdirectories: Frank2Maven, Frank2MultiConfig and Frank2MultiConfig_config. Frank2Maven shows how to have your Frank in a Maven project. This example has custom Java code, demonstrating how custom Java code can be included in your Frank.

Frank2MultiConfig and Frank2MultiConfig_config belong together. This example demonstrates how to have independent Frank configs. Each of these configs can be hosted in its own git repository, and it can follow its own DTAP (development-test-acceptance-deployment) cycle.

## TODO

* TODO: Move ibisdoc.xsd to a common location.