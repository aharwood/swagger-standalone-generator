package com.wotifgroup.swaggerstandalonegenerator.model;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import org.apache.commons.lang3.ClassUtils;

/**
 * A type, either for input or output, in the swagger format.
 *
 * User: Adam
 * Date: 27/07/12
 * Time: 11:11 PM
 */
public class ValueType {

    private boolean multiValue;
    private String type;
    private Class<?> clazz;

    public ValueType(String id, Class<?> clazz) {
        this(id, clazz, false);
    }

    public ValueType(String id, Class<?> clazz, boolean multiValue) {
        this.type = id;
        this.clazz = clazz;
        this.multiValue = multiValue;
    }

    public static ValueType createFromReturnType(Method method) {
        Class<?> clazz = method.getReturnType();

        boolean multiValue = false;
        if (Collection.class.isAssignableFrom(clazz)) {
            multiValue = true;
            //use the generic type of the collection instead
            clazz = (Class<?>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
        }

        PrimitiveType primitiveType = PrimitiveType.findByClass(clazz);
        if (primitiveType == null) {
            return create(clazz.getName(), multiValue);
        } else {
            return create(primitiveType.getSwaggerId(), multiValue);
        }
    }

    public static ValueType create(String id, boolean multiValue) {
        Class<?> clazz = null;
        PrimitiveType primitiveType = PrimitiveType.findBySwaggerId(id);
        if (primitiveType != null) {
            //primitive type
            clazz = primitiveType.getClazz();
        } else {
            //must be a complex type
            try {
                clazz = ClassUtils.getClass(id);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Could not locate domain class: " + id);
            }
            id = ClassUtils.getShortClassName(clazz);
        }

        return new ValueType(id, clazz, multiValue);
    }

    /**
     * @return The type of the type, which either maps to a primitive name or one of the
     * complex types.
     */
    public String getId() {
        if (multiValue) {
            return "List[" + type + "]";
        } else {
            return type;
        }
    }

    /**
     * A variant for when the id used in the model, since it is inconsistent with the rest of the document.
     */
    public String getModelId() {
        if (multiValue) {
            return "Array";
        } else {
            return type;
        }
    }

    /**
     * @return The class of the type.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    public String getType() {
        return type;
    }

    public boolean isMultiValue() {
        return multiValue;
    }
}
