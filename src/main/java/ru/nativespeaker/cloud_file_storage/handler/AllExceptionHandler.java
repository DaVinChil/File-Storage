package ru.nativespeaker.cloud_file_storage.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nativespeaker.cloud_file_storage.dto.ExceptionResponse;
import ru.nativespeaker.cloud_file_storage.exception.InternalServerException;
import ru.nativespeaker.cloud_file_storage.exception.NoSuchFileException;
import ru.nativespeaker.cloud_file_storage.exception.UserAlreadyExistsException;

import java.util.concurrent.atomic.AtomicLong;

@RestControllerAdvice
public class AllExceptionHandler {
    private final AtomicLong atomicLong = new AtomicLong(0);

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse internalServerError(InternalServerException e){
        return new ExceptionResponse(e.getMessage(), atomicLong.getAndIncrement());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse userAlreadyExists(UserAlreadyExistsException e){
        return new ExceptionResponse(e.getMessage(), atomicLong.getAndIncrement());
    }

    @ExceptionHandler({NoSuchFileException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badFileName(Exception e) {
        System.out.println("HANDELLING");
        return new ExceptionResponse(e.getMessage(), atomicLong.getAndIncrement());
    }
}
