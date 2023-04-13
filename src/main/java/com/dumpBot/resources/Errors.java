package com.dumpBot.resources;

public class Errors {
    private String errorReservation;
    private String commonError;
    private String authError;

    public String getErrorReservation() {
        return errorReservation;
    }

    public void setErrorReservation(String value) {
        this.errorReservation = value;
    }

    public String getCommonError() {
        return commonError;
    }

    public void setCommonError(String value) {
        this.commonError = value;
    }

    public String getAuthError() {
        return authError;
    }

    public void setAuthError(String value) {
        this.authError = value;
    }
}
