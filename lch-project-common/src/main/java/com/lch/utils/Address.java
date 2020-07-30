package com.lch.utils;

import lombok.Data;

@Data
public class Address {

    private int status;

    private String message;

    private Result result;

    @Override
    public String toString() {
        return "Address{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
