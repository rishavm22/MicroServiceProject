package com.learn.microservice.useraccount.exceptions.error;

public enum GlobalErrorCode implements ErrorCode {

    ERR_DEFAULT,

    ERR_ACCESS_DENIED,

    ERR_USER_ALREADY_EXIST,

    ERR_INVALID_CREDENTIALS,

    ERR_USER_NOT_FOUND;
}
