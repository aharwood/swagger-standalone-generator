package com.wotifgroup.swaggerjsongenerator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single RESTful endpoint.
 *
 * User: Adam
 * Date: 22/07/12
 * Time: 12:33 AM
 */
public class Resource {

    private String path;
    private String description;
    private Map<String, ResourcePath> pathToResourcePaths;
    private Collection<ValueType> models;

    public Resource(String path, String description) {
        this.path = path;
        this.description = description;
        this.pathToResourcePaths = new HashMap<String, ResourcePath>();
        this.models = new ArrayList<ValueType>();
    }

    public void addValueType(ValueType valueType) {
        this.models.add(valueType);
    }

    /**
     * Gets the ResourcePath mapped to this path. Creates a new one if it
     * does not already exist.
     *
     * @param path The relative path of the resource.
     * @return The corresponding ResourcePath.
     */
    public ResourcePath getResourcePath(String path) {
        ResourcePath resourcePath = this.pathToResourcePaths.get(path);
        if (resourcePath == null) {
            resourcePath = new ResourcePath(path);
            this.pathToResourcePaths.put(path, resourcePath);
        }

        return resourcePath;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public Collection<ResourcePath> getResourcePaths() {
        return pathToResourcePaths.values();
    }

    public Collection<ValueType> getModels() {
        return models;
    }
}
