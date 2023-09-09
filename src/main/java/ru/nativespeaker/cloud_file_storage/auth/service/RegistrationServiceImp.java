package ru.nativespeaker.cloud_file_storage.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthTokenGenerator;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthToken;
import ru.nativespeaker.cloud_file_storage.auth.user.User;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.handler.exception.UserAlreadyExistsException;
import ru.nativespeaker.cloud_file_storage.auth.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImp implements RegistrationService{
    private final UserRepository userRepository;
    private final AuthTokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<String> register(AuthorizationRequest request) {
        if(userRepository.existsByEmail(request.getLogin())){
            throw new UserAlreadyExistsException("User with " + request.getLogin() + " login already exists");
        }

        User user = User.builder()
                .email(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        AuthToken token = tokenGenerator.getUniqueToken();
        user.setToken(token);
        userRepository.save(user);

        return token.getUuid().describeConstable();
    }
}
