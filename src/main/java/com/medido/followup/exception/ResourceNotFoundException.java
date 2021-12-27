package com.medido.followup.exception;

public class ResourceNotFoundException extends FollowupException {

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
