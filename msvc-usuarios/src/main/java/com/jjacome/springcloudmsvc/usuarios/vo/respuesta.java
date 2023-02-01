package com.jjacome.springcloudmsvc.usuarios.vo;

import java.util.List;
public class respuesta<T> {

    private Integer code = 200;
    private String message;
    private List<String> errors;
    private T data;

    public respuesta() {
    }

    public respuesta(Integer code, String message, List<String> errors, T data) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
