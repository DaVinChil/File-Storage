package ru.nativespeaker.cloud_file_storage.auth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.user.User;
import ru.nativespeaker.cloud_file_storage.exception.InternalServerException;
import ru.nativespeaker.cloud_file_storage.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public boolean isExist(String token) {
        return tokenRepository.existsByUuid(token);
    }

    public boolean isValid(String token) {
        if(!isExist(token)) {
            return false;
        }
        Token entityToken = tokenRepository.findByUuid(token).get();
        if(entityToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }
}
