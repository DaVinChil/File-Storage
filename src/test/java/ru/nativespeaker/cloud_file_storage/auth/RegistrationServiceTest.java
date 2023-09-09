package ru.nativespeaker.cloud_file_storage.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.auth.service.RegistrationServiceImp;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthToken;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthTokenGenerator;
import ru.nativespeaker.cloud_file_storage.auth.user.UserRepository;
import ru.nativespeaker.cloud_file_storage.handler.exception.UserAlreadyExistsException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthTokenGenerator tokenGenerator;

    @InjectMocks
    private RegistrationServiceImp registrationService;

    @Test
    public void register_shouldReturn() {
        when(userRepository.existsByEmail(any())).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn("enfpwnd-ewkndsf");

        String uuid = UUID.randomUUID().toString();

        when(tokenGenerator.getUniqueToken()).thenAnswer(inv ->
                AuthToken.builder().uuid(uuid).build());

        Optional<String> res = registrationService.register(
                new AuthorizationRequest("login", "password"));
        assertTrue(res.isPresent());
        assertEquals(uuid, res.get());
    }

    @Test
    public void register_shouldFail() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        assertThrows(UserAlreadyExistsException.class,
                () -> registrationService.register(new AuthorizationRequest("login", "password")));
    }
}
