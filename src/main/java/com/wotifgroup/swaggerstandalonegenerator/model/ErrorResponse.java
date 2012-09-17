package com.wotifgroup.swaggerstandalonegenerator.model;

/**
 * User: Adam
 * Date: 27/07/12
 * Time: 11:13 PM
 */
public class ErrorResponse {

    private String reason;
    private Integer statusCode;

    public ErrorResponse(String reason, Integer statusCode) {
        this.reason = reason;
        this.statusCode = statusCode;
    }

    public String getReason() {
        return reason;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
