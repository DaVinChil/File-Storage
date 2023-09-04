package ru.nativespeaker.cloud_file_storage.handler.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String msg){
        super(msg);
    }
}
