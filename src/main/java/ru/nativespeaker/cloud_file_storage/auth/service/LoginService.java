package ru.nativespeaker.cloud_file_storage.auth.service;

import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;

public interface LoginService {
    String login(AuthorizationRequest request);
}
