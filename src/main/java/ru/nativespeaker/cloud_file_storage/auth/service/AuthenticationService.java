package ru.nativespeaker.cloud_file_storage.auth.service;

import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationRequest;

public interface AuthenticationService {
    String register(AuthenticationRequest request);
    String authenticate(AuthenticationRequest request);
}
