import java.io.File
import java.net.URL
import java.nio.file.Paths

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import org.slf4j.Logger
import org.slf4j.LoggerFactory

def log = LoggerFactory.getLogger('org.apache.camel.archetype')


log.info "Downloading Swagger API from ${request.properties['swaggerApiUrl']}..."

def url = new URL(request.properties['swaggerApiUrl'])
def apiText = url.text



log.info "Writing Swagger API definition to swagger-api.json file..."

def fileDir = Paths.get(request.outputDirectory, request.artifactId, 'src/main/swagger').toFile()
def fileName = 'swagger-api.json'
def file = new File(fileDir, fileName)
fileDir.mkdirs();
file.setText(JsonOutput.prettyPrint(apiText))



log.info "Parsing Swagger API definition..."

def jsonSlurper = new JsonSlurper()
def apiMap = jsonSlurper.parseText(apiText)

def apiLicense = apiMap?.info?.license?.name
def apiLicenseUrl = apiMap?.info?.license?.url
def apiTitle = apiMap?.info?.title
def apiTermsOfServiceUrl = apiMap?.info?.termsOfService
def apiVersion = apiMap?.info?.version
def apiDescription = apiMap?.info?.description
def apiContact = apiMap?.info?.contact?.email

def apiNames = apiMap?.tags*.name
if (!apiNames || apiNames?.empty) {
  apiNames = apiMap?.paths*.value.collectMany() { path ->
    path*.value.collectMany() { verb -> 
      verb?.tags?:[]
    } 
  } as Set
}
if (!apiNames || apiNames?.empty) {
  apiNames = ['default']
}



log.info "Modifying application.yml file..."

def quoteYamlString(def yamlString) {
  return (yamlString) ? "'${yamlString.replaceAll('\'', '\'\'')}'" : null
}

def applicationYmlFile = Paths.get(request.outputDirectory, request.artifactId, 'src/main/resources', 'application.yml').toFile()
def applicationYamlText = applicationYmlFile.text
applicationYamlText = applicationYamlText.replaceAll('__CAMEL_SPRINGBOOT_NAME__', request.artifactId.split('-').collect() { it.capitalize() }.join(''))
applicationYamlText = applicationYamlText.replaceAll('__API_LICENSE__', quoteYamlString(apiLicense)?:'')
applicationYamlText = applicationYamlText.replaceAll('__API_LICENSE_URL__', quoteYamlString(apiLicenseUrl)?:'')
applicationYamlText = applicationYamlText.replaceAll('__API_TITLE__', quoteYamlString(apiTitle)?:'')
applicationYamlText = applicationYamlText.replaceAll('__API_TERMS_OF_SERVICE_URL__', quoteYamlString(apiTermsOfServiceUrl)?:'')
applicationYamlText = applicationYamlText.replaceAll('__API_VERSION__', quoteYamlString(apiVersion)?:'')
applicationYamlText = applicationYamlText.replaceAll('__API_DESCRIPTION__', quoteYamlString(apiDescription)?:'')
applicationYamlText = applicationYamlText.replaceAll('__API_CONTACT__', quoteYamlString(apiContact)?:'')
applicationYmlFile.setText(applicationYamlText)



log.info "Modifying CamelConfiguration.java file..."

def apiClassNames = apiNames.collect() { name ->
  name = "${request.properties['package']}.api.${name.capitalize()}Api"
}
def camelConfigurationJavaFile = Paths.get(request.outputDirectory, request.artifactId, 'src/main/java', request.properties['package'].replaceAll('\\.', File.separator), 'CamelConfiguration.java').toFile()
def camelConfigurationJavaText = camelConfigurationJavaFile.text
camelConfigurationJavaText = camelConfigurationJavaText.replaceAll('__API_NAME__', apiTitle?:'Application')
camelConfigurationJavaText = camelConfigurationJavaText.replaceAll('__API_CLASSES__', apiClassNames.join(','))
camelConfigurationJavaFile.setText(camelConfigurationJavaText)
