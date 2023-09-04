package ru.nativespeaker.cloud_file_storage.auth.dto;

import lombok.Data;

@Data
public class AuthorizationRequest {
    private String login;
    private String password;
}
