package ru.nativespeaker.cloud_file_storage.auth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthTokenGenerator {
    private final AuthTokenRepository tokenRepository;

    private String getUniqueUuid() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (tokenRepository.existsByUuid(uuid));
        return uuid;
    }

    public AuthToken getUniqueToken(){
        AuthToken token = AuthToken.builder()
                .uuid(getUniqueUuid())
                .expirationDate(LocalDateTime.now().plusHours(12))
                .build();
        return token;
    }
}
