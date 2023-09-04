package ru.nativespeaker.cloud_file_storage.auth.service;

import ru.nativespeaker.cloud_file_storage.auth.dto.AuthorizationRequest;

public interface AuthenticationService {
    String register(AuthorizationRequest request);
    String authenticate(AuthorizationRequest request);
}
