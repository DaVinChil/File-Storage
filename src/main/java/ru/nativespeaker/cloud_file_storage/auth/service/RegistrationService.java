package ru.nativespeaker.cloud_file_storage.auth.service;

import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationRequest;

import java.util.Optional;

public interface RegistrationService {
    Optional<String> register(AuthenticationRequest request);
}
