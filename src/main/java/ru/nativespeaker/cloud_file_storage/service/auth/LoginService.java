package ru.nativespeaker.cloud_file_storage.service.auth;

import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;

public interface LoginService {
    String login(AuthorizationRequest request);
}
