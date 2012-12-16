package com.wotifgroup.swaggerstandalonegenerator.model

import com.wotifgroup.swaggerstandalonegenerator.JerseyToSwaggerAnnotationProcessor
import java.lang.reflect.Method

/**
 * User: Adam
 * Date: 8/10/12
 * Time: 8:18 PM
 */
class ValueTypeTest extends GroovyTestCase {

    void testGetResponseTypeShouldHandlePrimtiveString() {
        ValueType valueType = ValueType.create("string", false)
        assertEquals("string", valueType.id)
        assertEquals(String.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveInteger() {
        ValueType valueType = ValueType.create("int", false)
        assertEquals("int", valueType.id)
        assertEquals(Integer.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveLong() {
        ValueType valueType = ValueType.create("long", false)
        assertEquals("long", valueType.id)
        assertEquals(Long.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveDouble() {
        ValueType valueType = ValueType.create("double", false)
        assertEquals("double", valueType.id)
        assertEquals(Double.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveBoolean() {
        ValueType valueType = ValueType.create("boolean", false)
        assertEquals("boolean", valueType.id)
        assertEquals(Boolean.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandlePrimtiveDate() {
        ValueType valueType = ValueType.create("Date", false)
        assertEquals("Date", valueType.id)
        assertEquals(Date.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandleComplexType() {
        ValueType valueType = ValueType.create("java.util.Locale", false)
        assertEquals("Locale", valueType.id)
        assertEquals(Locale.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandleListOfComplexTypes() {
        ValueType valueType = ValueType.create("java.util.Locale", true)
        assertEquals("List[Locale]", valueType.id)
        assertEquals("Array", valueType.modelId)
        assertEquals(Locale.class, valueType.clazz)
    }

    void testGetResponseTypeShouldHandleListOfPrimitiveTypes() {
        ValueType valueType = ValueType.create("string", true)
        assertEquals("List[string]", valueType.id)
        assertEquals("Array", valueType.modelId)
        assertEquals(String.class, valueType.clazz)
    }
}
