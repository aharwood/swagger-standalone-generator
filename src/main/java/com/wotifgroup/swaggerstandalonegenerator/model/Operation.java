package com.wotifgroup.swaggerstandalonegenerator.model;

import java.util.Collection;
import java.util.List;

/**
 * Represents an operation which can be performed on a resource path.
 *
 * User: Adam
 * Date: 27/07/12
 * Time: 11:04 PM
 */
public class Operation {

    private String httpMethod;
    private String nickname;
    private ValueType responseType;
    private String notes;
    private String summary;

    private Collection<ErrorResponse> errorResponses;
    private List<Parameter> parameters;

    public Operation(String httpMethod, String nickname, String notes, String summary, ValueType responseType, Collection<ErrorResponse> errorResponses, List<Parameter> parameters) {
        this.httpMethod = httpMethod;
        this.nickname = nickname;
        this.notes = notes;
        this.summary = summary;
        this.responseType = responseType;
        this.errorResponses = errorResponses;
        this.parameters = parameters;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getNickname() {
        return nickname;
    }

    public ValueType getResponseType() {
        return responseType;
    }

    public String getNotes() {
        return notes;
    }

    public String getSummary() {
        return summary;
    }

    public Collection<ErrorResponse> getErrorResponses() {
        return errorResponses;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
