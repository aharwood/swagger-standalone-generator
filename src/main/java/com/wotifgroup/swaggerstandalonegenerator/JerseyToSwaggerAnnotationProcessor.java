package com.wotifgroup.swaggerstandalonegenerator;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wotifgroup.swaggerstandalonegenerator.model.Operation;
import com.wotifgroup.swaggerstandalonegenerator.model.PrimitiveType;
import com.wotifgroup.swaggerstandalonegenerator.model.ResourcePath;
import com.wotifgroup.swaggerstandalonegenerator.model.Resource;
import com.wotifgroup.swaggerstandalonegenerator.model.ValueType;
import java.util.Set;
import javax.ws.rs.Path;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * The main AnnotationProcessor, which will parse over the jersey resources and use a SwaggerJsonWriter to
 * write the output.
 *
 * User: Adam
 * Date: 19/07/12
 * Time: 9:24 PM
 */
public class JerseyToSwaggerAnnotationProcessor implements AnnotationProcessor {

    private Set<AnnotationTypeDeclaration> annotationTypeDeclarations;
    private AnnotationProcessorEnvironment annotationProcessorEnvironment;

    public JerseyToSwaggerAnnotationProcessor(Set<AnnotationTypeDeclaration> annotationTypeDeclarations,
                                              AnnotationProcessorEnvironment annotationProcessorEnvironment) {
        this.annotationTypeDeclarations = annotationTypeDeclarations;
        this.annotationProcessorEnvironment = annotationProcessorEnvironment;
    }

    public void process() {
        SwaggerJsonWriter writer = new SwaggerJsonWriter(this.annotationProcessorEnvironment);

        for (TypeDeclaration nextTypeDecl : this.annotationProcessorEnvironment.getTypeDeclarations()) {
            Resource resource = getResource(nextTypeDecl);

            for (MethodDeclaration nextMethod : nextTypeDecl.getMethods()) {
                addOperation(resource, nextMethod);
            }

            writer.write(resource);
        }

        writer.flushAndClose();
    }

    protected void addOperation(Resource resource, MethodDeclaration nextMethod) {
        ApiOperation apiOperation = nextMethod.getAnnotation(ApiOperation.class);
        if (apiOperation != null) {
            //documented method
            //should also be annotated with @Path
            Path path = nextMethod.getAnnotation(Path.class);
            if (path == null) {
                throw new IllegalStateException("Method marked with ApiOperation did not also have a Path annotation");
            }

            ValueType responseValueType = getResponseType(apiOperation);
            resource.addValueType(responseValueType);

            Operation operation = new Operation(apiOperation.httpMethod(),
                                                nextMethod.getSimpleName(),
                                                apiOperation.value(),
                                                apiOperation.notes(),
                                                responseValueType,
                                                //TODO
                                                null,
                                                //TODO
                                                null);
            ResourcePath resourcePath = resource.getResourcePath(path.value());
            resourcePath.addOperation(operation);
        }
    }

    /**
     * Parses out the ValueType of the response returned by the operation.
     *
     * @param apiOperation The operation.
     * @return Its response ValueType.
     */
    protected ValueType getResponseType(ApiOperation apiOperation) {
        return getValueType(apiOperation.responseClass(), apiOperation.multiValueResponse());
    }

    protected ValueType getValueType(String id, boolean multiValue) {
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
            //swagger spec is to lower camel case just the final portion of the class name
            id = StringUtils.uncapitalize(ClassUtils.getShortClassName(clazz));
        }

        if (multiValue) {
            id = "List[" + id + "]";
        }

        return new ValueType(id, clazz);
    }

    protected Resource getResource(TypeDeclaration typeDeclaration) {
        Api api = typeDeclaration.getAnnotation(Api.class);
        Resource resource = new Resource(api.value(), api.description());

        return resource;
    }
}
