package ru.nativespeaker.cloud_file_storage.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionResponse {
    private String message;
    private Long id;
}
