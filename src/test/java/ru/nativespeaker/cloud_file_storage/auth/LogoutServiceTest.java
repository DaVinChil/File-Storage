package ru.nativespeaker.cloud_file_storage.auth;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogoutServiceTest {

    @Mock
    private AuthTokenRepository tokenRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LogoutService logoutService;

    @Test
    public void logout_shouldThrowUnauthenticated() {
        when(authentication.isAuthenticated()).thenReturn(false);
        assertThrows(InternalServerException.class, () -> logoutService.logout(null, null, authentication));
    }

    @Test
    public void logout_shouldThrowNoToken() {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(User.builder().token(null).build());

        assertThrows(InternalServerException.class, () -> logoutService.logout(null, null, authentication));
    }

    @Test
    public void logout_shouldPassSameToken() {
        when(authentication.isAuthenticated()).thenReturn(true);

        AuthToken token = AuthToken.builder().build();
        when(authentication.getPrincipal()).thenReturn(User.builder().token(token).build());

        ArgumentCaptor<AuthToken> valueCapture = ArgumentCaptor.forClass(AuthToken.class);
        doNothing().when(tokenRepository).delete(valueCapture.capture());

        assertEquals(token, valueCapture.getValue());
    }
}
