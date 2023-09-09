package ru.nativespeaker.cloud_file_storage.auth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private final AuthTokenRepository tokenRepository;

    public boolean isExist(String token) {
        return tokenRepository.existsByUuid(token);
    }


    public boolean isValid(String token) {
        if(!isExist(token)) {
            return false;
        }
        AuthToken entityToken = tokenRepository.findByUuid(token).get();
        if(entityToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }

    @Transactional
    public void delete(String token) {
        tokenRepository.deleteByUuid(token);
    }
}
