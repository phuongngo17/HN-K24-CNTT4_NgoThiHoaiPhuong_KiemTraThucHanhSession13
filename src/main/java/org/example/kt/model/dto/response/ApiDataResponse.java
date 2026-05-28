package org.example.kt.model.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiDataResponse <T>{
    private Boolean success;
    private String message;
    private T data;
    private HttpStatus httpStatus;
}
