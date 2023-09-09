package ru.nativespeaker.cloud_file_storage.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthToken;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthTokenRepository;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthTokenService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AuthTokenServiceTest {
    @Mock
    private AuthTokenRepository authTokenRepository;

    @InjectMocks
    private AuthTokenService authTokenService;

    @Test
    public void isExists_true() {
        when(authTokenRepository.existsByUuid(any())).thenReturn(true);

        assertTrue(authTokenService.isExist(UUID.randomUUID().toString()));
    }

    @Test
    public void isExists_false() {
        when(authTokenRepository.existsByUuid(any())).thenReturn(false);

        assertFalse(authTokenService.isExist(UUID.randomUUID().toString()));
    }

    @Test
    public void isValid_true() {
        when(authTokenRepository.existsByUuid(any())).thenReturn(true);
        when(authTokenRepository.findByUuid(any())).thenReturn(Optional.of(
                AuthToken.builder().expirationDate(LocalDateTime.now().plusHours(12)).build()
        ));

        assertTrue(authTokenService.isValid(UUID.randomUUID().toString()));
    }

    @Test
    public void isValid_false() {
        when(authTokenRepository.existsByUuid(any())).thenReturn(true);
        when(authTokenRepository.findByUuid(any())).thenReturn(Optional.of(
                AuthToken.builder().expirationDate(LocalDateTime.now().plusHours(-12)).build()
        ));

        assertTrue(authTokenService.isValid(UUID.randomUUID().toString()));
    }
}
