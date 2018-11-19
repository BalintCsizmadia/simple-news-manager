package com.newsmanager.web.service.exception;

class ServiceException extends Exception {

    ServiceException() {
        super();
    }

    ServiceException(String message) {
        super(message);
    }

    ServiceException(Throwable throwable) {
        super(throwable);
    }

    ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
