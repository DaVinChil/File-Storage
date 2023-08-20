package ru.nativespeaker.cloud_file_storage.dto;

import lombok.Data;

@Data
public class AuthorizationRequest {
    private String login;
    private String password;
}
