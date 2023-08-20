package ru.nativespeaker.cloud_file_storage.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String msg){
        super(msg);
    }
}
