package ru.nativespeaker.cloud_file_storage.handler.exception;

public class NoSuchFileException extends RuntimeException{
    public NoSuchFileException(String message) {
        super(message);
    }
}
