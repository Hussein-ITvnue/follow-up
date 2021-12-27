package com.medido.followup.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDetail {

    private String error;
    private Integer status;
    private String pointer;
    private String details;

}
