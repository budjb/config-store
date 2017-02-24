# Introduction
The **config-store** application is a standard Grails 3 application that can be run from the command line
with a minimum of configuration. This document will help you get the application up and running quickly.

# Requirements
The application is fairly standalone, and only requires a server to run the application, JDK 8, and
by default a MySQL database server. The database server software can be easily changed based on the
configuration file.

# Building
Once the source code is checked out from [GitHub](https://github.rackspace.com/PrivateCloudOps/config-store), run
the following command to build the application.
```
./gradlew assemble
```
This will generate a `.war` file under `build/libs`. Place this file wherever the application
will reside on the application server.

# Configuration
The configuration file for this application only needs the configuration values for the database,
Identity, and an encryption password. The following template can be used as a starting point.

```json
{
    "database.url": "valid JDBC connection string",
    "database.username": "database username",
    "database.password": "database password",
    "identity.url": "URL to Global Auth/Identity",
    "identity.accounts.vdo.username":"administrator Global Auth/Identity username",
    "identity.accounts.vdo.password":"administrator Global Auth/Identity password",
    "config-store.crypto.password":"strong password"
}
```

Place this file somewhere on the filesystem that is accessible by the application. Its full path must be
provided to the application when it is run, as described below.

By default, a MySQL database server is required. To use a different platform, changes to the
application's `application.gaml` file need to be made to account for the different Hibernate diablect.

# Starting
The application is meant to be run using only the **Java** interpreter, and not using
a servlet container like **Tomcat**. The application has an embedded **Tomcat** servlet
container to simplify deployment.

To run the application, the following command line can be used as a starting point.
```bash
JSON_DICTIONARY_PATH=/path/to/config/file java -Dgrails.env=prod -jar filename.war -server Xmx768M -XX:MaxPermSize=256M
```
This will start the application, binding it to port *8080*. Standard **Java** command line
options can be used to tweak the runtime parameters of the JVM or the application.
