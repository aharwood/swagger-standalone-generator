package com.wotifgroup.swaggerjsongenerator.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a resource on a RESTful endpoint,
 * which can have multiple HTTP methods run against it (e.g. GET, PUT, POST).
 *
 * User: Adam
 * Date: 22/07/12
 * Time: 12:33 AM
 */
public class ResourcePath {

    private String path;
    private String description;

    private Collection<Operation> operations;

    public ResourcePath(String path) {
        this.path = path;

        this.operations = new ArrayList<Operation>();
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Operation> getOperations() {
        return operations;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
