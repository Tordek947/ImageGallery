package net.atlassian.cmathtutor.exception;

import lombok.AllArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
public class IntegrationException extends Exception {

    private ErrorType errorType;

    @Override
    public String getMessage() {
        return errorType.toString();
    }

    public static enum ErrorType {
        INTERNAL_SERVER_ERROR,
        CLIENT_ERROR,
        OTHER_ERROR
    }
}
