package com.mapto.api.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResponse implements Serializable {

    private Boolean success;
    private String message;
    private Integer code;
    private Object data;

    public ApiResponse(StatusCode statusCode, Object data) {
        this.success = statusCode == StatusCode.CODE_200;
        this.code = statusCode.getCode();
        this.message = statusCode.getMsg();
        this.data = data;
    }

    public ApiResponse(StatusCode statusCode){
        this.success = statusCode == StatusCode.CODE_200;
        this.code = statusCode.getCode();
        this.message = statusCode.getMsg();
    }
}
