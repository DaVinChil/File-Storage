package ru.nativespeaker.cloud_file_storage.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthTokenGenerator;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthToken;
import ru.nativespeaker.cloud_file_storage.auth.user.User;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationRequest;
import ru.nativespeaker.cloud_file_storage.handler.AllExceptionHandler;
import ru.nativespeaker.cloud_file_storage.handler.exception.UserAlreadyExistsException;
import ru.nativespeaker.cloud_file_storage.auth.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImp implements RegistrationService {
    private final UserRepository userRepository;
    private final AuthTokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LogManager.getLogger(RegistrationServiceImp.class);
    private final Level REQUEST_LEVEL = Level.forName("request", 1);


    @Override
    public Optional<String> register(AuthenticationRequest request) {
        if (userRepository.existsByEmail(request.getLogin())) {
            throw new UserAlreadyExistsException("User with " + request.getLogin() + " login already exists");
        }

        User user = User.builder()
                .email(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        AuthToken token = tokenGenerator.getUniqueToken();
        user.setToken(token);
        userRepository.save(user);

        logger.log(REQUEST_LEVEL, String.format("%s created account", user.getEmail()));

        return token.getUuid().describeConstable();
    }
}
