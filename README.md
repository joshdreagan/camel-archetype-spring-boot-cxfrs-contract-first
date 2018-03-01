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