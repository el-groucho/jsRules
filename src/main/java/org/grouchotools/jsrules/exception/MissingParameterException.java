package org.grouchotools.jsrules.exception;

/**
 * Created by Paul Richardson 4/30/2015
 */
public class MissingParameterException extends InvalidParameterException {
    public MissingParameterException() {
    }

    public MissingParameterException(String message) {
        super(message);
    }

    public MissingParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingParameterException(Throwable cause) {
        super(cause);
    }

    public MissingParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
