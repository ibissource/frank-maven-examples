# Frank2MultiConfig and Frank2MultiConfig_config

## Directory structure

In general, this directory structure is as follows:

    |--projects
       |--frank-runner
       |--frank2application
          |--war
          |  |--src
          |  |  |--...
          |  |--pom.xml
          |--ear
          |  |--src
          |  |  |--...
          |  |--pom.xml
          |--build.xml
          |--pom.xml
          |--restart.bat
       |--frank2application_config1
          |--src
          |  |  |--main
          |  |     |--configuration
          |  |        |--Config1
          |--build.xml
          |--pom.xml
          |--restart.bat
       |--frank2application_config2
          |--...

In a real development environment, every subdirectory of `projects` is a separate Git repository that can be started separately with the Frank!Runner. The Frank!Runner thus recognizes two directory structures. First, it recognizes the war/ear project:

    |--frank2application
       |--war
       |  |--src
       |  |  |--...
       |  |--pom.xml
       |--ear
       |  |--src
       |  |  |--...
       |  |--pom.xml
       |--build.xml
       |--pom.xml
       |--restart.bat

The Frank!Runner recognizes this structure by the presence the directory `war` and within that directory the file `pom.xml`. In other words, this directory structure is recognized by the presence of file `war/pom.xml`. If you do not want to produce an `ear`, you can safely omit `frank2application/ear`.

The second directory structure is the separate configuration:

    |--frank2application_config1
       |--src
       |  |  |--main
       |  |     |--configuration
       |  |        |--Config1
       |--build.xml
       |--pom.xml
       |--restart.bat

This structure is recognized by the presence of `pom.xml`.

## History

The directory structure of Frank2Maven requires all configurations to be in a single Git repository. Sometimes, the different configurations are maintained by different teams. In this case, they should follow different CI/CD cycles. They should be in different Git repositories. Furthermore, it should be possible to deploy the configurations independently. This directory structure achieves all this. In the example, the base project is "Frank2MultiConfig", while the separate configuration is "Frank2MultiConfig_config".

## Frank vs Frank config

The project "Frank2MultiConfig_config" holds a Frank config. The project "Frank2MultiConfig" is needed for common Frank config and for custom Java code. Custom java classes can only be loaded when the Frank!Framework boots, not when a Frank config is imported later. Therefore, custom Java code has to appear in the base project.

## Running with Frank!Runner

TODO: Write this.
TODO: Move src/main/configuration to src/main/configuration/custom and remove the config for the maven-resource-plugin

## Built artifact

Building Frank2MultiConfig produces two artifacts, a web application with extension `.war` and an Enterprise Archive with extension `.ear`. The `.ear` is meant for deployment on Websphere Application Server.

## Build process

To build Frank2MultiConfig, go to its directory and issue `mvn clean install -Drevision=1.0` on the command line. The property `revision` sets the common version number for all artifacts you are building here. To build Frank2MultiConfig_config, go to its directory and issue `mvn clean install` on a command line.

## Explanation of the example pom.xml

TODO: Explain how all the `pom.xml` files cooperate.

## Artifact deployment

Only deploying the web application is explained here. The Enterprise Archive was never deployed. Only its general structure was checked: it contains a web application and it contains the file `application.xml`.

To deploy the web application on Apache Tomcat, please do the following:

* Reuse the Tomcat installation you created for Frank2Maven.
* Update catalina.properties. Property `configurations.names` should get the value `${instance.name.lc},custom`.
* Add the following lines to `catalina.properties`:

      configurations.custom.classLoaderType=DatabaseClassLoader
      jdbc.migrator.active=true

* Deploy the ".war" artifact produces by Frank2MultiConfig. Modify the file name to be `multi-config.war`, otherwise you will have a complicated context path.
* Start your Tomcat server.
* Go to the upload configuration page of the Frank!Console. Upload the artifact produced by Frank2Config_config.
* Try the adapter. It is the same adapter as you had in Frank2Maven.
