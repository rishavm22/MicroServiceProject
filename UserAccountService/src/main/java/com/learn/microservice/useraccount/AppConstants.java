package com.learn.microservice.useraccount;

import lombok.experimental.UtilityClass;

import java.net.URI;

@UtilityClass
public class AppConstants {

    public static final String AUTH_KEY = "Authorization";

    public static final String AUTH_HEADER_PREFIX = "Bearer ";

    public static final String PASSWORD_EXPR = "(?=[A-Za-z0-9@#$%^&+!=]+$)^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+!=])(?=.{8,}).*$";


    public static final int TOKEN_EXPIRY = 1000 * 60 * 60;

    public static final int REFRESH_TOKEN_EXPIRY = 1000 * 60 * 60 * 12;



    @UtilityClass
    public static final class ErrorConstants {

        public static final String ERR_VALIDATION = "error.validation";
        public static final URI DEFAULT_TYPE = URI.create("https://abc/problem/problem-with-message");


    }
}
