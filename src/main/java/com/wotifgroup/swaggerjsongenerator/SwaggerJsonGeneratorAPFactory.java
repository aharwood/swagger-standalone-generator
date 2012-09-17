package com.wotifgroup.swaggerjsongenerator;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * User: Adam
 * Date: 19/07/12
 * Time: 9:25 PM
 */
public class SwaggerJsonGeneratorAPFactory implements AnnotationProcessorFactory {

    public Collection<String> supportedAnnotationTypes() {
        return Arrays.asList("com.wordnik.swagger.annotations.Api");
    }

    public Collection<String> supportedOptions() {
        return Collections.emptySet();
    }

    public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> annotationTypeDeclarations,
                                               AnnotationProcessorEnvironment annotationProcessorEnvironment) {
        return new JerseyToSwaggerAnnotationProcessor(annotationTypeDeclarations, annotationProcessorEnvironment);
    }
}
