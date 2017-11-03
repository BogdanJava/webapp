package com.bogdan.exceptions;

public class DataNotValidException extends Exception {
    public DataNotValidException() {
        super();
    }

    public DataNotValidException(String message) {
        super(message);
    }

    public DataNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotValidException(Throwable cause) {
        super(cause);
    }

    protected DataNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
