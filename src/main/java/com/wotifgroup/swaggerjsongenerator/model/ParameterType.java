package com.wotifgroup.swaggerjsongenerator.model;

/**
 * User: Adam
 * Date: 12/09/12
 * Time: 8:20 PM
 */
public enum ParameterType {

    REQUEST_BODY("body"),
    PATH_PARAM("path");

    private String swaggerParamType;

    ParameterType(String swaggerParamType) {
        this.swaggerParamType = swaggerParamType;
    }

    public String getSwaggerParamType() {
        return swaggerParamType;
    }
}
