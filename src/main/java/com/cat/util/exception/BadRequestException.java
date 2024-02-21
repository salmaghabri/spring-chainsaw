package com.cat.util.exception;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

public class BadRequestException extends AbstractAppException {

    public BadRequestException(String mainResource) {
        super(HTTP_BAD_REQUEST, mainResource);
    }

}
