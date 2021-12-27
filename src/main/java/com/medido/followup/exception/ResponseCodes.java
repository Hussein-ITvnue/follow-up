package com.medido.followup.exception;

import lombok.Getter;

@Getter
public enum ResponseCodes {

    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHENTICATED(401, "UNAUTHENTICATED"),
    FORBIDDEN(403, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    CONFLICT(409, "CONFLICT"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    private int code;
    private String type;

    ResponseCodes(int code, String type) {
        this.code = code;
        this.type = type;
    }
}
