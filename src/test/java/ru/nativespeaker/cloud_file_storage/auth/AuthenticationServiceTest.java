package ru.nativespeaker.cloud_file_storage.auth;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationRequest;
import ru.nativespeaker.cloud_file_storage.auth.service.AuthenticationServiceImp;
import ru.nativespeaker.cloud_file_storage.auth.service.LoginService;
import ru.nativespeaker.cloud_file_storage.auth.service.RegistrationService;
import ru.nativespeaker.cloud_file_storage.handler.exception.InternalServerException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private LoginService loginService;
    @Mock
    private RegistrationService registrationService;
    @InjectMocks
    private AuthenticationServiceImp authenticationService;

    @Test
    public void authenticate_sameToken() {
        String token = "token";
        when(loginService.login(any())).thenReturn(token);
        assertEquals(token, authenticationService.authenticate(new AuthenticationRequest("login", "password")));
    }

    @Test
    public void authenticate_shouldRegister() {
        String token = "token";
        when(loginService.login(any())).thenReturn(null);
        when(registrationService.register(any())).thenReturn(token.describeConstable());
        assertEquals(token, authenticationService.authenticate(new AuthenticationRequest("login", "password")));
    }

    @Test
    public void register_shouldReturnSameToken() {
        String token = "token";
        when(registrationService.register(any())).thenReturn(token.describeConstable());
        assertEquals(token, authenticationService.register(new AuthenticationRequest("login", "password")));
    }

    @Test
    public void register_shouldThrowInternalServerException() {
        when(registrationService.register(any())).thenReturn(Optional.empty());
        assertThrows(InternalServerException.class, () -> authenticationService.register(new AuthenticationRequest("login", "password")));
    }
}
