package com.sebrown2023.exceptions;

public class NoElementException extends RuntimeException {
    public NoElementException(String elementName) {
        super(elementName + " was not found.");
    }
}
