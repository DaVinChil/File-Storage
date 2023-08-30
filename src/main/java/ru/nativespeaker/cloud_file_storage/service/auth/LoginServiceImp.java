package ru.nativespeaker.cloud_file_storage.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.data_model.Token;
import ru.nativespeaker.cloud_file_storage.data_model.User;
import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImp implements LoginService {
    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public Optional<String> login(AuthorizationRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getLogin());
        if (user.isEmpty()) {
            return Optional.empty();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        Token token = tokenGenerator.getUniqueToken(user.get());
        return token.getUuid().describeConstable();
    }
}
