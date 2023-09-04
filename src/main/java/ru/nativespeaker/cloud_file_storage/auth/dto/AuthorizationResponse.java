package ru.nativespeaker.cloud_file_storage.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizationResponse {
    @JsonProperty("auth-token")
    private String authToken;
}
