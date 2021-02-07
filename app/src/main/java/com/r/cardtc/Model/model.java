package com.r.cardtc.Model;

public class model {
    String code,details,cause;

    public model() {
    }

    public model(String code, String details, String cause) {
        this.code = code;
        this.details = details;
        this.cause = cause;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
