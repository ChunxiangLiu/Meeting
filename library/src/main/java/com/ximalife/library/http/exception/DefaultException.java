package com.ximalife.library.http.exception;

public class DefaultException extends RuntimeException {

    private int code = -781197537;

    public DefaultException(String message) {
        super(message);
    }

    public DefaultException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
