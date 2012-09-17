package com.wotifgroup.swaggerjsongenerator

import com.sun.mirror.apt.AnnotationProcessorEnvironment
import com.sun.mirror.apt.Filer
import com.wotifgroup.swaggerjsongenerator.model.Resource
import com.wotifgroup.swaggerjsongenerator.model.ResourcePath
import groovy.json.JsonSlurper
import com.wotifgroup.swaggerjsongenerator.model.Operation
import com.wotifgroup.swaggerjsongenerator.model.Parameter
import com.wotifgroup.swaggerjsongenerator.model.ValueType
import com.wotifgroup.swaggerjsongenerator.model.ParameterType
import com.wotifgroup.swaggerjsongenerator.model.ErrorResponse

/**
 * User: Adam
 * Date: 27/07/12
 * Time: 9:00 PM
 */
class SwaggerJsonWriterTest extends GroovyTestCase {

    void testFlushAndCloseShouldWriteResourcesJson() {
        StringWriter outputWriter = new StringWriter()
        def environment = mockEnvironment([:], outputWriter)
        SwaggerJsonWriter writer = new SwaggerJsonWriter(environment)
        writer.write(new Resource("/path0", "description 0"))
        writer.write(new Resource("/path1", null))
        writer.flushAndClose()
        def responseJson = new JsonSlurper().parseText(outputWriter.toString())
        assertEquals("/path0", responseJson.apis[0].path)
        assertEquals("description 0", responseJson.apis[0].description)
        assertEquals("/path1", responseJson.apis[1].path)
    }

    void testResourcesJsonIncludesApiVersion() {
        StringWriter outputWriter = new StringWriter()
        def environment = mockEnvironment(["-AapiVersion=1.2.1": null], outputWriter)
        SwaggerJsonWriter writer = new SwaggerJsonWriter(environment)
        writer.flushAndClose()
        def responseJson = new JsonSlurper().parseText(outputWriter.toString())
        assertEquals("1.0", responseJson.swaggerVersion)
        assertEquals("1.2.1", responseJson.apiVersion)
        assertTrue(responseJson.apis.isEmpty())
    }

    void testResourcesJsonIncludesBasePath() {
        StringWriter outputWriter = new StringWriter()
        def environment = mockEnvironment(["-AbasePath=http://wotsync.wotifgroup.com": null], outputWriter)
        SwaggerJsonWriter writer = new SwaggerJsonWriter(environment)
        writer.flushAndClose()
        def responseJson = new JsonSlurper().parseText(outputWriter.toString())
        assertEquals("http://wotsync.wotifgroup.com", responseJson.basePath)
    }

    void testResourceDescriptionFileShouldBeWritten() {
        StringWriter outputWriter = new StringWriter()
        def environment = mockEnvironment([
            "-AapiVersion=1.2.1": null,
            "-AbasePath=http://wotsync.wotifgroup.com": null
        ], outputWriter)
        SwaggerJsonWriter writer = new SwaggerJsonWriter(environment)
        Resource resource = new Resource("/path0", "description 0")
        resource.getResourcePath("/operation0")
        writer.write(resource)
        def responseJson = new JsonSlurper().parseText(outputWriter.toString())
        assertEquals("1.2.1", responseJson.apiVersion)
        assertEquals("/path0", responseJson.resourcePath)
        assertEquals("/operation0", responseJson.apis[0].path)
        assertEquals("description 0", responseJson.apis[0].description)
    }

    void testResourceDescriptionFileShouldContainOperations() {
        StringWriter outputWriter = new StringWriter()
        def environment = mockEnvironment([
        "-AapiVersion=1.2.1": null,
        "-AbasePath=http://wotsync.wotifgroup.com": null
        ], outputWriter)
        SwaggerJsonWriter writer = new SwaggerJsonWriter(environment)
        Resource resource = new Resource("/path0", "description 0")
        ResourcePath path0 = resource.getResourcePath("/path0")
        path0.setDescription("path description 0")
        path0.addOperation(new Operation("GET",
                                         "getById",
                                         "notes0",
                                         "summary0",
                                         new ValueType("string", String.class),
                                         [],
                                         []))
        writer.write(resource)
        def responseJson = new JsonSlurper().parseText(outputWriter.toString())
        assertEquals("GET", responseJson.apis[0].operations[0].httpMethod)
        assertEquals("summary0", responseJson.apis[0].operations[0].summary)
        assertEquals("notes0", responseJson.apis[0].operations[0].notes)
        assertEquals("string", responseJson.apis[0].operations[0].responseClass)
        assertEquals("getById", responseJson.apis[0].operations[0].nickname)
    }

    void testResourceDescriptionFileShouldContainOperationParameters() {
        StringWriter outputWriter = new StringWriter()
        def environment = mockEnvironment([
        "-AapiVersion=1.2.1": null,
        "-AbasePath=http://wotsync.wotifgroup.com": null
        ], outputWriter)
        SwaggerJsonWriter writer = new SwaggerJsonWriter(environment)
        Resource resource = new Resource("/path0", "description 0")
        ResourcePath path0 = resource.getResourcePath("/path0")
        path0.setDescription("path description 0")
        path0.addOperation(new Operation("GET", "getById", null, null, new ValueType("string", String.class),
                                         [],
                                         [new Parameter("param0",
                                                        "paramDescription0",
                                                        new ValueType("string", String.class),
                                                        true,
                                                        ParameterType.PATH_PARAM,
                                                        false),
                                         new Parameter("param1",
                                                       "paramDescription1",
                                                       new ValueType("Locale", Locale.class),
                                                       false,
                                                       ParameterType.REQUEST_BODY,
                                                       true)]))
        writer.write(resource)
        def responseJson = new JsonSlurper().parseText(outputWriter.toString())

        assertEquals("param0", responseJson.apis[0].operations[0].parameters[0].name)
        assertEquals("paramDescription0", responseJson.apis[0].operations[0].parameters[0].description)
        assertEquals("path", responseJson.apis[0].operations[0].parameters[0].paramType)
        assertEquals("string", responseJson.apis[0].operations[0].parameters[0].dataType)
        assertEquals(true, responseJson.apis[0].operations[0].parameters[0].required)
        assertEquals(false, responseJson.apis[0].operations[0].parameters[0].allowMultiple)

        assertEquals("param1", responseJson.apis[0].operations[0].parameters[1].name)
        assertEquals("paramDescription1", responseJson.apis[0].operations[0].parameters[1].description)
        assertEquals("body", responseJson.apis[0].operations[0].parameters[1].paramType)
        assertEquals("Locale", responseJson.apis[0].operations[0].parameters[1].dataType)
        assertEquals(false, responseJson.apis[0].operations[0].parameters[1].required)
        assertEquals(true, responseJson.apis[0].operations[0].parameters[1].allowMultiple)
    }

    void testResourceDescriptionFileShouldContainOperationErrorResponses() {
        StringWriter outputWriter = new StringWriter()
        def environment = mockEnvironment([
        "-AapiVersion=1.2.1": null,
        "-AbasePath=http://wotsync.wotifgroup.com": null
        ], outputWriter)
        SwaggerJsonWriter writer = new SwaggerJsonWriter(environment)
        Resource resource = new Resource("/path0", "description 0")
        ResourcePath path0 = resource.getResourcePath("/path0")
        path0.setDescription("path description 0")
        path0.addOperation(new Operation("GET", "getById", null, null, new ValueType("string", String.class),
                                         [new ErrorResponse("Resource Not Found", 404),
                                          new ErrorResponse("Malformed request", 400)],
                                         []))
        writer.write(resource)
        def responseJson = new JsonSlurper().parseText(outputWriter.toString())

        assertEquals(404, responseJson.apis[0].operations[0].errorResponses[0].code)
        assertEquals("Resource Not Found", responseJson.apis[0].operations[0].errorResponses[0].reason)

        assertEquals(400, responseJson.apis[0].operations[0].errorResponses[0].code)
        assertEquals("Malformed request", responseJson.apis[0].operations[0].errorResponses[0].reason)
    }

    void testOptionShouldReturnValueIfMatchingKey() {
        def environment = mockEnvironment(
            ["optionName0": "optionValue0",
             "-AoptionName1=optionValue1": null]
        )
        SwaggerJsonWriter writer = new SwaggerJsonWriter(environment)
        assertEquals("optionValue1", writer.getOption("optionName1"))
    }

    void testOptionShouldReturnNullIfNoMatchingKey() {
        def environment = mockEnvironment(
            ["optionName0": "optionValue0",
            "-AoptionName1=optionValue1": null]
        )
        SwaggerJsonWriter writer = new SwaggerJsonWriter(environment)
        assertNull(writer.getOption("optionName0"))
    }

    void testGetFileNameShouldUseWholePathForRootLevel() {
        assertEquals("rootLevel",
                     new SwaggerJsonWriter(null).getFileName(new Resource("rootLevel", null)))
    }

    void testGetFileNameShouldUseLastPathElement() {
        assertEquals("leaf",
                     new SwaggerJsonWriter(null).getFileName(new Resource("/root/middle/leaf", null)))
    }

    def mockEnvironment(Map<String, String> options, StringWriter writer = new StringWriter()) {
        [
            getOptions: { -> options },
            getFiler: { ->
                [
                    createTextFile: {location, packageName, file, charset ->
                        //reset the output
                        writer.getBuffer().setLength(0)
                        new PrintWriter(writer)
                    }
                ] as Filer
            }
        ] as AnnotationProcessorEnvironment
    }
}
