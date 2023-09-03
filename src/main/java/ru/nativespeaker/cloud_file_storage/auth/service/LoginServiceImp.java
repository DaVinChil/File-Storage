package ru.nativespeaker.cloud_file_storage.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.auth.token.TokenGenerator;
import ru.nativespeaker.cloud_file_storage.auth.token.Token;
import ru.nativespeaker.cloud_file_storage.user.User;
import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImp implements LoginService {
    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(AuthorizationRequest request) {
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

        Token token = tokenGenerator.getUniqueToken();
        User loginUser = user.get();
        loginUser.setToken(token);
        userRepository.save(loginUser);
        return token.getUuid();
    }
}
