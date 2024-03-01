package com.cms.example.cms.feature.content.exception;

public class ContentStorageException extends RuntimeException {
    public ContentStorageException(String message) {
        super(message);
    }
    public ContentStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
