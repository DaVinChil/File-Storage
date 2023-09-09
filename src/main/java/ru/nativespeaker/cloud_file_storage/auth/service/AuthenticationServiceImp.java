package ru.nativespeaker.cloud_file_storage.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationRequest;
import ru.nativespeaker.cloud_file_storage.handler.exception.InternalServerException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{
    private final LoginService loginService;
    private final RegistrationService registrationService;
    @Override
    public String register(AuthenticationRequest request) {
        return registrationService.register(request).orElseThrow(() -> new InternalServerException("Internal server exception"));
    }

    @Override
    public String authenticate(AuthenticationRequest request) {
        var token = loginService.login(request);
        if(token == null) {
            token = register(request);
        }
        return token;
    }
}
