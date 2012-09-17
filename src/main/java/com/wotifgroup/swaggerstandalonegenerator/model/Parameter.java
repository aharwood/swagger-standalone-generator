package com.wotifgroup.swaggerstandalonegenerator.model;

/**
 * Represents a parameter to an operation.
 *
 * User: Adam
 * Date: 27/07/12
 * Time: 11:12 PM
 */
public class Parameter {

    private String name;
    private String description;
    private ValueType modelType;
    private boolean required = false;
    private ParameterType paramType;
    private boolean allowMultiple = false;

    public Parameter(String name, String description, ValueType modelType, boolean required, ParameterType paramType, boolean allowMultiple) {
        this.name = name;
        this.description = description;
        this.required = required;
        this.paramType = paramType;
        this.allowMultiple = allowMultiple;
        this.modelType = modelType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ValueType getModelType() {
        return modelType;
    }

    public boolean isRequired() {
        return required;
    }

    public ParameterType getParamType() {
        return paramType;
    }

    public boolean isAllowMultiple() {
        return allowMultiple;
    }
}
