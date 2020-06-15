# Frank2Maven

## Directory structure

In general, this directory structure is as follows:

    pom.xml - Configuration file for Maven. The Frank!Runner recognizes this directory structure when it sees this file.
    |- src/main/java - Custom Java classes.
    |- src/main/configurations
       |- custom
          |- Configuration.xml
    |- build.xml - For integration with your development environment

The Frank!Runner checks whether your project contains a file named `pom.xml` at your project root. If so, the Frank!Runner assumes that the directory structure is as shown.

## History

This is first directory structure for Frank development that uses the build tool Maven.

## Frank vs Frank config

With this directory structure, your project will hold a Frank. You include multiple Frank configurations within the same Git repository. Your build results in a single artifact that holds all the configurations.

## Running with Frank!Runner

Your project needs to include file `build.xml` with the contents given in the README.md file of the frank-runner. Follow the instructions given with the Frank!Runner to setup your development environment. You can then start your project from your development environment, either Eclipse or Visual Studio Code.

## Built artifact

Building your project produces a web application (file extension .war). Both the Frank!Framework and your configurations are included.

## Build process

To build this example, go to directory `Frank2Maven`. In general: the directory that holds your `pom.xml` file. Then run `mvn clean install`.

## Explanation of the example pom.xml

At the top, you see a `<packaging>war</packaging>`. This tells Maven that it has to build a .war file. One of the dependencies is:

    <dependency>
      <groupId>org.ibissource</groupId>
      <artifactId>ibis-adapterframework-webapp</artifactId>
      <version>${ffVersion}</version>
      <type>war</type>
    </dependency>

This dependency grabs the Web application of the Frank!Framework. It says `<type>war</type>` to specify that this dependency is not a .jar, but a web application. Dependencies with type `war` are not transitive, which means that you do not get the Java classes. The `ibis-adapterframework-webapp` artifact does not hold any Java code. The Java code only appears in its dependencies. The Java classes of the Frank!Framework are grabbed with the following dependency:

    <dependency>
      <groupId>org.ibissource</groupId>
      <artifactId>ibis-adapterframework-core</artifactId>
      <version>${ffVersion}</version>
    </dependency>

This is an ordinary dependency, so transitive. All dependencies of the Frank!Framework source code are included in the web application.

Finally, the `pom.xml` should put your files in `src/main/configurations` on the Java classpath. This is the purpose of the lower part:

    <build>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-resources-plugin</artifactId>
          <version>2.3</version>
        </plugin>
      </plugins>
      <resources>
        <resource>
          <directory>src/main/configurations</directory>
        </resource>
      </resources>
    </build>

This example contains a custom Java class. This class is handled automatically by Maven without the need for further configuration.

In your own project, you may extend this `pom.xml` to include deploying your artifact. Then you need to build with `mvn clean deploy`.

## Artifact deployment

After building, you can deploy this example as follows:

* Download Apache Tomcat.
* Put your .war file in the `webapps` folder.
* Put the following extra files in your `lib` directory:

  * `activemq-core-5.6.0.jar`
  * `geronimo-j2ee-management_1.1_spec-1.0.1.jar`
  * `geronimo-jms_1.1_spec-1.1.1.jar`
  * `geronimo-jta_1.1_spec-1.1.1.jar`
  * `h2-1.4.200.jar` if you use a H2 database, otherwise you need the driver for your other database.
  * `kahadb-5.6.0.jar`
  * `service-dispatcher-1.5.jar`

  You can find these on Maven Central or at the site of your database vendor.

  TODO: Is this list enough for other projects?

* Add the following to your `catalina.properties`:

      instance.name=withCustom
      dtap.stage= ... the DTAP stage you want ...
      configurations.names=custom

* Edit `conf/context.xml` to reference the database:

      <Resource
        name="jdbc/withcustom"
        type="org.h2.jdbcx.JdbcDataSource"
        factory="org.apache.naming.factory.BeanFactory"
        URL="jdbc:h2:C: ... your Tomcat installation dir .../logs/withCustom" />
