package ru.nativespeaker.cloud_file_storage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.dto.AuthorizationResponse;

public interface AuthenticationController {
    @PostMapping("/login")
    AuthorizationResponse login(AuthorizationRequest request);
}
