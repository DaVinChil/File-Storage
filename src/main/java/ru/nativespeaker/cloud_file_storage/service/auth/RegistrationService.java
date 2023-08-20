package ru.nativespeaker.cloud_file_storage.service.auth;

import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;

import java.util.Optional;

public interface RegistrationService {
    Optional<String> register(AuthorizationRequest request);
}
