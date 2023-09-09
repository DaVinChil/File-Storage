package ru.nativespeaker.cloud_file_storage.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthTokenGenerator;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthToken;
import ru.nativespeaker.cloud_file_storage.auth.user.User;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationRequest;
import ru.nativespeaker.cloud_file_storage.auth.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImp implements LoginService {
    private final AuthTokenGenerator tokenGenerator;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(AuthenticationRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getLogin());
        if (user.isEmpty()) {
            return null;
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        AuthToken token = tokenGenerator.getUniqueToken();
        User loginUser = user.get();
        loginUser.setToken(token);
        userRepository.save(loginUser);
        return token.getUuid();
    }
}
