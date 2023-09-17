package ru.nativespeaker.cloud_file_storage.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import ru.nativespeaker.cloud_file_storage.auth.service.LogoutService;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthToken;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthTokenRepository;
import ru.nativespeaker.cloud_file_storage.auth.user.User;
import ru.nativespeaker.cloud_file_storage.handler.exception.InternalServerException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogoutServiceTest {

    @Mock
    private AuthTokenRepository tokenRepository;

    @InjectMocks
    private LogoutService logoutService;

    @Test
    public void logout_shouldThrowUnauthenticated() {
        var req = mock(HttpServletRequest.class);
        when(req.getHeader(any())).thenReturn(null);
        assertThrows(InternalServerException.class, () -> logoutService.logout(req, null, null));
    }

    @Test
    public void logout_shouldThrowNoSuchToken() {
        var req = mock(HttpServletRequest.class);
        when(req.getHeader("auth-token")).thenReturn("Bearer " + UUID.randomUUID());
        when(tokenRepository.findByUuid(any())).thenReturn(Optional.empty());

        assertThrows(InternalServerException.class, () -> logoutService.logout(req, null, null));
    }

    @Test
    public void logout_shouldPassSameToken() {
        var req = mock(HttpServletRequest.class);
        String uuid = "Bearer " + UUID.randomUUID();
        when(req.getHeader("auth-token")).thenReturn(uuid);

        AuthToken token = AuthToken.builder().uuid(uuid).build();
        when(tokenRepository.findByUuid(any())).thenReturn(Optional.of(token));

        ArgumentCaptor<AuthToken> valueCapture = ArgumentCaptor.forClass(AuthToken.class);
        doNothing().when(tokenRepository).delete(valueCapture.capture());

        logoutService.logout(req, null, null);

        assertEquals(token, valueCapture.getValue());
    }
}
