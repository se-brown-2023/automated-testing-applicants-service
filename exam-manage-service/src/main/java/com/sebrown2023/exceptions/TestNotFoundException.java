package com.sebrown2023.exceptions;

public class TestNotFoundException extends NoElementException {
    public TestNotFoundException(long testId) {
        super("Test with id" + testId);
    }
}
