package ru.nativespeaker.cloud_file_storage.auth.service;

import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationRequest;

public interface LoginService {
    String login(AuthenticationRequest request);
}
