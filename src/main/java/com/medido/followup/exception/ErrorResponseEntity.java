package com.medido.followup.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponseEntity {

    private List<ErrorResponseDetail> errors = new ArrayList<>();

    public ErrorResponseEntity append(ErrorResponseDetail errorResponseDetail) {
        errors.add(errorResponseDetail);
        return this;
    }

}
