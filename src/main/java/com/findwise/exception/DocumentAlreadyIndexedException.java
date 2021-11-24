package com.findwise.exception;

public class DocumentAlreadyIndexedException extends Exception{
    public DocumentAlreadyIndexedException(String id) {
        super("Collection already contains Document with :" + id );
    }
}
