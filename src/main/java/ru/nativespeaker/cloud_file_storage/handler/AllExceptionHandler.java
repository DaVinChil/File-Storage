package ru.nativespeaker.cloud_file_storage.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.nativespeaker.cloud_file_storage.auth.user.User;
import ru.nativespeaker.cloud_file_storage.handler.dto.ExceptionResponse;
import ru.nativespeaker.cloud_file_storage.handler.exception.InternalServerException;
import ru.nativespeaker.cloud_file_storage.handler.exception.NoSuchFileException;
import ru.nativespeaker.cloud_file_storage.handler.exception.UserAlreadyExistsException;

import java.util.concurrent.atomic.AtomicLong;

@RestControllerAdvice
public class AllExceptionHandler extends ResponseEntityExceptionHandler {
    private final AtomicLong atomicLong = new AtomicLong(0);
    private final Logger logger = LogManager.getLogger(AllExceptionHandler.class);
    private final Level REQUEST_LEVEL = Level.forName("request", 1);

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse internalServerError(InternalServerException e) {
        long id = atomicLong.getAndIncrement();
        log(e.getMessage(), id, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ExceptionResponse(e.getMessage(), id);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse userAlreadyExists(UserAlreadyExistsException e) {
        long id = atomicLong.getAndIncrement();
        log(e.getMessage(), id, HttpStatus.BAD_REQUEST);
        return new ExceptionResponse(e.getMessage(), id);
    }

    @ExceptionHandler({NoSuchFileException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badFileName(Exception e) {
        long id = atomicLong.getAndIncrement();
        log(e.getMessage(), id, HttpStatus.BAD_REQUEST);
        return new ExceptionResponse(e.getMessage(), id);
    }

    private void log(String msg, long id, HttpStatus status) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.log(REQUEST_LEVEL, String.format("%30s %8s %20s %20s %d %d %s FAIL",
                principal.getEmail(),
                request.getMethod(),
                request.getServletPath(),
                status.name(),
                status.value(),
                id,
                msg));
    }
}