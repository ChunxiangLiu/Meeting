package com.ximalife.library.http.exception;

public class TokenException extends RuntimeException {

    private int code = -781197537;

    public TokenException(String message) {
        super(message);
    }

    public TokenException(int code, String message) {
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
