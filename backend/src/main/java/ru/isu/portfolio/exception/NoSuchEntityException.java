package ru.isu.portfolio.exception;

public class NoSuchEntityException extends RuntimeException{
    public NoSuchEntityException(String message) {
        super(message);
    }
}
