package ru.nativespeaker.cloud_file_storage.exception;

public class NoSuchFileException extends RuntimeException{
    public NoSuchFileException(String message) {
        super(message);
    }
}
