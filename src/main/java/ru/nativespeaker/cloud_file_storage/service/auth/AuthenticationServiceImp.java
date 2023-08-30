package ru.nativespeaker.cloud_file_storage.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.exception.InternalServerException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{
    private final LoginService loginService;
    private final RegistrationService registrationService;
    @Override
    public String register(AuthorizationRequest request) {
        return registrationService.register(request).orElseThrow(() -> new InternalServerException("Internal server exception"));
    }

    @Override
    public String authenticate(AuthorizationRequest request) {
        var token = loginService.login(request).orElse(null);
        if(token == null) {
            token = register(request);
        }
        return token;
    }
}
