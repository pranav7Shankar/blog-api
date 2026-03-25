package com.webApp.bloggingapp.exceptions;

public class DuplicateResourceException extends RuntimeException {
    String resourceName;
    String fieldName;
    String fieldValue;
    public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with %s: %s already exists", resourceName, fieldName, fieldValue ));
        this.fieldName = fieldName;
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
