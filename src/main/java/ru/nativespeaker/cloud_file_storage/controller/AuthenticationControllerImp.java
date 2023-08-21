package ru.nativespeaker.cloud_file_storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.dto.AuthorizationResponse;
import ru.nativespeaker.cloud_file_storage.service.auth.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImp implements AuthenticationController{
    private final AuthenticationService authenticationService;
    @Override
    public AuthorizationResponse authenticate(AuthorizationRequest request) {
        return new AuthorizationResponse(authenticationService.authenticate(request));
    }
}
