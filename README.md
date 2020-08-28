# Camel/Spring Boot: CXFRS Contract First Archetype

## Building the project

```
$ mvn clean install
```

## Using the archetype

```
$ cd <PROJECT_DESTINATION_DIRECTORY>
$ mvn archetype:generate -DarchetypeCatalog=local \
                         -DarchetypeGroupId=org.apache.camel.archetypes \
                         -DarchetypeArtifactId=camel-archetype-spring-boot-cxfrs-contract-first \
                         -DarchetypeVersion=1.0.0-SNAPSHOT \
                         -DgroupId=<PROJECT_GROUP_ID> \
                         -DartifactId=<PROJECT_ARTIFACT_ID> \
                         -Dversion=<PROJECT_VERSION> \
                         -Dpackage=<PROJECT_PACKAGE> \
                         -Dswagger-api-url=<SWAGGER_API_URL>
```

__Note: It seems that a [bug](https://issues.apache.org/jira/projects/ARCHETYPE/issues/ARCHETYPE-565) was introduced in the Maven Archetype Plugin in version 3.1.0 that breaks this project. So until a fix is released, you'll need to provide the fully qualified plugin name and version for the above command (ie, `mvn org.apache.maven.plugins:maven-archetype-plugin:3.0.1:generate ...`).__