package ru.nativespeaker.cloud_file_storage.service;

import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;

public interface AuthenticationService {
    String register(AuthorizationRequest request);
    String authenticate(AuthorizationRequest request);
}
