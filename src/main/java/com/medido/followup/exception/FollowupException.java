package com.medido.followup.exception;

public class FollowupException extends RuntimeException {

    public FollowupException(String message, Throwable cause) {
        super(message, cause);
    }

    public FollowupException(String message) {
        super(message);
    }

    public FollowupException(Throwable cause) {
        super(cause);
    }

}
