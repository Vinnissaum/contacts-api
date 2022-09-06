package com.vinnissaum.mycontactsspring.services.errors;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String msg) {
        super(msg);
    }

}
