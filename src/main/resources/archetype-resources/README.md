#set($dollar = '$')
#set($hash = '#')
${hash} ${artifactId}

${hash}${hash} Building the project

```
${dollar} mvn clean install
```

${hash}${hash} Running the project

```
${dollar} mvn spring-boot:run
```

${hash}${hash} Testing the project

To pull up the Swagger/OpenAPI spec:

```
${dollar} curl -X GET -H 'Accept: application/json' 'http://localhost:8080/services/camel/swagger.json'
```

