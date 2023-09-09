package ru.nativespeaker.cloud_file_storage.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.auth.service.LoginServiceImp;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthToken;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthTokenGenerator;
import ru.nativespeaker.cloud_file_storage.auth.user.User;
import ru.nativespeaker.cloud_file_storage.auth.user.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private AuthTokenGenerator generator;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private LoginServiceImp loginService;

    @Test
    public void login_shouldReturnNull() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertNull(loginService.login(new AuthorizationRequest("login", "password")));
    }

    @Test
    public void login_shouldReturnSame() {
        String token = "token";
        String login = "login";
        String pass = "password";
        User user = User.builder().email(login).password(pass).build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        doReturn(AuthToken.builder().uuid(token).build()).when(generator).getUniqueToken();
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        String resToken = loginService.login(new AuthorizationRequest(login, pass));

        assertEquals(resToken, token);

        User captUser = captor.getValue();

        assertEquals(captUser.getEmail(), login);
        assertEquals(captUser.getPassword(), pass);
        assertEquals(captUser.getToken().getUuid(), token);
    }


}
