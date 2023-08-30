package ru.nativespeaker.cloud_file_storage.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void logoutOptions(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Methods", "POST");
    }
}
