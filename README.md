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
The **config-store** is meant to be run using the standard VDO host-based configuration file.
This file should be placed at `/etc/grails-config/config-store-host.groovy`.

The configuration file for this application only needs the configuration values for the database,
Identity, and an encryption password. The following template can be used as a starting point.

```groovy
dataSource {
    url = 'changeme' // Use a standard jdbc connector string
    username = 'changeme'
    password = 'changeme'

    // Change these if using a different database server
    driverClassName = 'com.mysql.jdbc.Driver'
    dialect = org.hibernate.dialect.MySQL5InnoDBDialect
    dbCreate = 'update'

    properties {
        jmxEnabled = true
        initialSize = 5
        maxActive = 50
        minIdle = 5
        maxIdle = 25
        maxWait = 10000
        maxAge = 600000
        timeBetweenEvictionRunsMillis = 5000
        minEvictableIdleTimeMillis = 60000
        validationQuery = 'SELECT 1'
        validationQueryTimeout = 3
        validationInterval = 15000
        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = false
        jdbcInterceptors = 'ConnectionState'
        defaultTransactionIsolation = 2
    }
}

vdo {
    clients {
        globalAuth {
            v2 {
                enabled = true
                baseAddress = 'changeme'
                adminIdentifier = 'admin'

                accounts = [
                    [
                        username: 'changeme',
                        password: 'changeme',
                        identifier: 'admin',
                        type: 'system',
                        isDefault: true
                    ]
                ]
            }
        }
    }
}

crypto {
    password = 'generate some very long, very difficult password'
}
```

# Starting
The application is meant to be run using only the **Java** interpreter, and not using
a servlet container like **Tomcat**. The application has an embedded **Tomcat** servlet
container to simplify deployment.

To run the application, the following command line can be used as a starting point.
```
java -Dgrails.env=prod -jar filename.war -server Xmx768M -XX:MaxPermSize=256M
```
This will start the application, binding it to port *8080*. Standard **Java** command line
options can be used to tweak the runtime parameters of the JVM or the application.
