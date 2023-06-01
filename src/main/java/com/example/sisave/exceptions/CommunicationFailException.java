package com.example.sisave.exceptions;

public class CommunicationFailException extends RuntimeException {

    public CommunicationFailException(String message) {
        super(message);
    }


    public CommunicationFailException() {
        super();
    }
}
