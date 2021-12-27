package com.medido.followup.exception;

public class ResourceExistsException extends FollowupException {

    public ResourceExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceExistsException(String message) {
        super(message);
    }
}
