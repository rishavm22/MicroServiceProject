package com.learn.microservice.useraccount.exceptions.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorMessage {

    private Date timestamp;

    private String message;

    private  String trace;

    private String lineNumber;

    private String className;

    private String methodName;

    private String fileName;

    public ErrorMessage(String message) {
        this.timestamp = new Date();
        this.message = message;
    }
}
