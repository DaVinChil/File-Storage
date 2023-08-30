package ru.nativespeaker.cloud_file_storage.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.data_model.Token;
import ru.nativespeaker.cloud_file_storage.data_model.User;
import ru.nativespeaker.cloud_file_storage.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.exception.UserAlreadyExistsException;
import ru.nativespeaker.cloud_file_storage.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImp implements RegistrationService{
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
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
        Token token = tokenGenerator.getUniqueToken();
        user.setToken(token);
        userRepository.save(user);

        return token.getUuid().describeConstable();
    }
}
