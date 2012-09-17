package com.wotifgroup.swaggerjsongenerator

import com.wotifgroup.swaggerjsongenerator.model.ValueType

/**
 * User: Adam
 * Date: 1/08/12
 * Time: 7:52 PM
 */
class JerseyToSwaggerAnnotationProcessorTest extends GroovyTestCase {

    void testGetResponseTypeShouldHandlePrimtiveString() {
        def processor = new JerseyToSwaggerAnnotationProcessor(null, null)
        ValueType valueType = processor.getValueType("string", false)
        assertEquals("string", valueType.id)
        assertEquals(String.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveInteger() {
        def processor = new JerseyToSwaggerAnnotationProcessor(null, null)
        ValueType valueType = processor.getValueType("int", false)
        assertEquals("int", valueType.id)
        assertEquals(Integer.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveLong() {
        def processor = new JerseyToSwaggerAnnotationProcessor(null, null)
        ValueType valueType = processor.getValueType("long", false)
        assertEquals("long", valueType.id)
        assertEquals(Long.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveDouble() {
        def processor = new JerseyToSwaggerAnnotationProcessor(null, null)
        ValueType valueType = processor.getValueType("double", false)
        assertEquals("double", valueType.id)
        assertEquals(Double.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveBoolean() {
        def processor = new JerseyToSwaggerAnnotationProcessor(null, null)
        ValueType valueType = processor.getValueType("boolean", false)
        assertEquals("boolean", valueType.id)
        assertEquals(Boolean.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveDate() {
        def processor = new JerseyToSwaggerAnnotationProcessor(null, null)
        ValueType valueType = processor.getValueType("Date", false)
        assertEquals("Date", valueType.id)
        assertEquals(Date.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandleComplexType() {
        def processor = new JerseyToSwaggerAnnotationProcessor(null, null)
        ValueType valueType = processor.getValueType("java.util.Locale", false)
        assertEquals("locale", valueType.id)
        assertEquals(Locale.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandleListOfComplexTypes() {
        def processor = new JerseyToSwaggerAnnotationProcessor(null, null)
        ValueType valueType = processor.getValueType("java.util.Locale", true)
        assertEquals("List[locale]", valueType.id)
        assertEquals(Locale.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandleListOfPrimitiveTypes() {
        def processor = new JerseyToSwaggerAnnotationProcessor(null, null)
        ValueType valueType = processor.getValueType("string", true)
        assertEquals("List[string]", valueType.id)
        assertEquals(String.class, valueType.clazz)
    }
}
