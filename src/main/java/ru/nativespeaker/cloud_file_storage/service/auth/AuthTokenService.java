package ru.nativespeaker.cloud_file_storage.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.data_model.Token;
import ru.nativespeaker.cloud_file_storage.data_model.User;
import ru.nativespeaker.cloud_file_storage.exception.InternalServerException;
import ru.nativespeaker.cloud_file_storage.repository.TokenRepository;
import ru.nativespeaker.cloud_file_storage.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public boolean isExist(String token){
        return tokenRepository.existsByUuid(token);
    }

    public String getUserEmail(String token){
        return tokenRepository.findByUuid(token).map(userRepository::findByToken).map(Optional::orElseThrow).map(User::getEmail).orElse(null);
    }
}
