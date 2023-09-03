package ru.nativespeaker.cloud_file_storage.auth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private final TokenRepository tokenRepository;

    public boolean isExist(String token) {
        return tokenRepository.existsByUuid(token);
    }

    @Transactional
    public boolean isValidOrDelete(String token) {
        if(!isExist(token)) {
            return false;
        }
        Token entityToken = tokenRepository.findByUuid(token).get();
        if(entityToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(entityToken);
            return false;
        }
        return true;
    }
}
