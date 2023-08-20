package ru.nativespeaker.cloud_file_storage.service.auth;

import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;

public interface RegistrationService {
    String register(AuthorizationRequest request);
}
