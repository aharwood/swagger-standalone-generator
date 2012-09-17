package com.wotifgroup.swaggerjsongenerator.model;

/**
 * A type, either for input or output, in the swagger format.
 *
 * User: Adam
 * Date: 27/07/12
 * Time: 11:11 PM
 */
public class ValueType {

    private String id;
    private Class<?> clazz;

    public ValueType(String id, Class<?> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    /**
     * @return The id of the type, which either maps to a primitive name or one of the
     * complex types.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The class of the type.
     */
    public Class<?> getClazz() {
        return clazz;
    }
}
