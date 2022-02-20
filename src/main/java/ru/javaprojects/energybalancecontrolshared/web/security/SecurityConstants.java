package ru.javaprojects.energybalancecontrolshared.web.security;

public class SecurityConstants {
    public static final String BAD_TOKEN = "Auth token is invalid. Try to authorize";
    public static final String ACCESS_DENIED = "You do not have enough permission";
    public static final String NOT_AUTHORIZED = "You are not authorized";
    public static final String BAD_CREDENTIALS = "Email / password incorrect. Please try again";
    public static final String DISABLED = "Your account was disabled";

    private SecurityConstants() {
    }
}