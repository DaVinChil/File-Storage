package ru.nativespeaker.cloud_file_storage.auth.service;

import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;

import java.util.Optional;

public interface LoginService {
    Optional<String> login(AuthorizationRequest request);
}
