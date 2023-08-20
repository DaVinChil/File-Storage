package ru.nativespeaker.cloud_file_storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorizationResponse {
    @JsonProperty("auth-token")
    private String authToken;
}
