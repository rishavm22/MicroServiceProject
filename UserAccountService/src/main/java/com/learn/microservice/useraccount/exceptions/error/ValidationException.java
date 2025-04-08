package com.learn.microservice.useraccount.exceptions.error;

import com.learn.microservice.useraccount.AppConstants;
import lombok.Getter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ValidationException {
    private static final long serialVersionUID = 1L;

    @Getter
    private final String entityName;

    @Getter
    private final String errorKey;

    public ValidationException(String defaultMessage) {
        this(AppConstants.ErrorConstants.DEFAULT_TYPE, defaultMessage, null, null);
    }

    public ValidationException(String defaultMessage, String entityName, String errorKey) {
        this(AppConstants.ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public ValidationException(URI type, String defaultMessage, String entityName, String errorKey) {

//        super(type, defaultMessage, Status.BAD_REQUEST, null, null, null, getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", errorKey);
        parameters.put("params", entityName);

        return parameters;
    }
}
