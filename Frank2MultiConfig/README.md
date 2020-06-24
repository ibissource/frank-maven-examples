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

This structure is recognized by the presence of `pom.xml`. The Frank!Runner also expects that the name of the configuration matches the suffix in the project name. If the configuration is in the directory ``src/main/configuration/custom``, then the directory name of the Git project should be ``Frank2MultiConfig_custom`` (provided that the name of the main project is ``Frank2MultiConfig``).

## History

The directory structure of Frank2Maven requires all configurations to be in a single Git repository. Sometimes, the different configurations are maintained by different teams. In this case, they should follow different CI/CD cycles. They should be in different Git repositories. Furthermore, it should be possible to deploy the configurations independently. This directory structure achieves all this. In the example, the base project is "Frank2MultiConfig", while the separate configuration is "Frank2MultiConfig_config".

## Frank vs Frank config

The project "Frank2MultiConfig_config" holds a Frank config. The project "Frank2MultiConfig" is needed for common Frank config and for custom Java code. Custom java classes can only be loaded when the Frank!Framework boots, not when a Frank config is imported later. Therefore, custom Java code has to appear in the base project.

## Running with Frank!Runner

You can run "Frank2MultiConfig_config" with the Frank!Runner. Please follow the instructions of the Frank!Runner README.md file to have integration with your development environment, either Eclipse or Visual Studio Code. We already supplied the "build.xml" and "frank-runner.properties" files you need. You can use these as examples within your own project.

## Built artifact

Building Frank2MultiConfig produces two artifacts, a web application with extension `.war` and an Enterprise Archive with extension `.ear`. The `.ear` is meant for deployment on Websphere Application Server. Please note that no test was done about deploying the .ear artifact. We only checked that it has `application.xml` and that it contained the WAR archive.

## Build process

To build Frank2MultiConfig, go to its directory and issue `mvn clean install -Drevision=1.0` on the command line. The property `revision` sets the common version number for all artifacts you are building here. To build Frank2MultiConfig_config, go to its directory and issue `mvn clean install` on a command line.

## Explanation of the example pom.xml files

The two POM files `Frank2MultiConfig/pom.xml` and `Frank2MultiConfig_config/pom.xml` show the basis you need to get the artifacts out of your CI/CD pipeline. In your own project, you probably want bigger POM files to customize your artifacts and to include deployment to your Nexus repository. The POM files you have here will be explained below.

### Frank2MultiConfig/pom.xml

The purpose of `Frank2MultiConfig/pom.xml` is to combine the builds of the `war` and the `ear` subdirectory into one execution of Maven. Without this file, you would have to invoke Maven separately to build these two subdirectories. The line `<packaging>pom</packaging>` tells Maven that this POM aggregates projects and does not itself build artifacts. The projects being aggregated are in the `<modules>` tag. They are referenced by subdirectory.

When you call Maven from the `Frank2MultiConfig` directory, it will first analyze the referenced subprojects to determine the right order. For example, the subprojects may depend on each other, in which case the dependency is built before the dependent subproject. The Maven component doing this analysis is called the "reactor". You can Google this word if you want to know more.

### Frank2MultiConfig/war/pom.xml

Now we turn to `Frank2MultiConfig/war/pom.xml`. This one has `<packaging>war</packaging>`, indicate that this one builds the WAR file, the web application that can be deployed to Apache Tomcat. At the start, you see a `<parent>` tag:

    <parent>
      <artifactId>multi-config</artifactId>
      <groupId>org.ibissource</groupId>
      <version>${revision}</version>
    </parent>

This XML tells Maven that it should use many settings from `Frank2MultiConfig/pom.xml` when building `Frank2MultiConfig/war/pom.xml`. The settings from these two POM files are combined to get the effective configuration that Maven uses to build the WAR. Settings in the child POM have precedence over settings in the parent POM. With this inheritance relation in place, common configurations for the WAR and the EAR can be put in the parent POM. This inheritance is not essential, however. You could as well use another parent POM or no parent at all to build valid artifacts. It depends on the fine tuning you want.

The following part is the same as in `Frank2Maven/pom.xml`

    <dependency>
      <groupId>org.ibissource</groupId>
      <artifactId>ibis-adapterframework-webapp</artifactId>
      <version>${ffVersion}</version>
      <type>war</type>
    </dependency>
    <dependency>
      <groupId>org.ibissource</groupId>
      <artifactId>ibis-adapterframework-core</artifactId>
      <version>${ffVersion}</version>
    </dependency>

The top item with the line `<type>war</type>` grabs the web stuff from the Frank!Framework, but it is not transitive. This means that possible dependencies of artifact `ibis-adapterframework-webapp` are not considered. The bottom one adds Java classes of the Frank!Framework that your custom class `CustomPipe.java` needs. Maven packages WAR archives by including all Maven dependencies in the archive. This is different from packaging .jar files, which by default don't include the Maven dependencies.

If you do not need an EAR, then Maven allows you to change `Frank2MultiConfig/pom.xml` to just build the WAR file, doing away with the aggregation. This is not supported by the Frank!Runner however.

### Frank2MultiConfig/ear/pom.xml

Next, we turn to `Frank2MultiConfig/ear/pom.xml`. It has the line `<packaging>ear</packaging>`, telling Maven that it has to build an EAR. The inheritance relation is repeated:

    <parent>
      <artifactId>multi-config</artifactId>
      <groupId>org.ibissource</groupId>
      <version>${revision}</version>
    </parent>

The EAR POM also inherits settngs from `Frank2MultiConfig/pom.xml`. The effective configuration used to build the EAR is obtained by combining `Frank2MultiConfig/pom.xml` and `Frank2MultiConfig/ear/pom.xml`.

Next, you have:

    <dependencies>
      <dependency>
        <groupId>org.ibissource</groupId>
        <artifactId>multi-config-war</artifactId>
        <type>war</type>
        <version>${revision}</version>
      </dependency>    
    </dependencies>

This states that your WAR has to be included in the EAR being configured.

An EAR file is basically a .zip file that contains WAR files. The EAR file adds some configuration data, for example the context root of each included web application. It is the responsibility of the `maven-ear-plugin` Maven plugin to include the right files. To get the right metadata in your EAR archive, you need to configure the plugin as shown below:

    <plugin>
      <artifactId>maven-ear-plugin</artifactId>
      <version>3.0.2</version>
      <configuration>
        <archive> 
          <manifest> 
            <addDefaultImplementationEntries>true</addDefaultImplementationEntries> 
          </manifest> 
          <manifestEntries>
            <Ibis-Project>multi-config</Ibis-Project>
          </manifestEntries> 
        </archive> 
        <modules>
          <webModule>
            <groupId>org.ibissource</groupId>
            <artifactId>multi-config-war</artifactId>
            <bundleFileName>multi-config.war</bundleFileName>
            <contextRoot>/multi-config</contextRoot>
          </webModule>
        </modules>
      </configuration>
    </plugin>

### Frank2MultiConfig_config/pom.xml

Finally, we turn to `Frank2MultiConfig_config/pom.xml`. If this were your own project, directory `Frank2MultiConfig_config` would be a separate Git repository. Therefore, no aggregation is applied here. This POM just zips the files in `src/main/configuration`. This is not the default directory that Maven zips. Therefore, you need the `<resource>` tag as shown.

    <resource>
      <directory>src/main/configuration</directory>
    </resource>

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
