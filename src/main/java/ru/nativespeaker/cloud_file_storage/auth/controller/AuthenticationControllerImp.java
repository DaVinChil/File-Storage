package ru.nativespeaker.cloud_file_storage.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.nativespeaker.cloud_file_storage.auth.service.AuthenticationService;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationRequest;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationResponse;

@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImp implements AuthenticationController{
    private final AuthenticationService authenticationService;
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return new AuthenticationResponse(authenticationService.authenticate(request));
    }

    @Override
    public void logoutOptions(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Methods", "POST");
    }
}
