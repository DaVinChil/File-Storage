package ru.nativespeaker.cloud_file_storage.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nativespeaker.cloud_file_storage.data_model.Token;
import ru.nativespeaker.cloud_file_storage.data_model.User;
import ru.nativespeaker.cloud_file_storage.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenGenerator {
    private final TokenRepository tokenRepository;

    private String getUniqueUuid() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (tokenRepository.existsByUuid(uuid));
        return uuid;
    }

    public Token getUniqueToken(User user){
        Token token = Token.builder()
                .uuid(getUniqueUuid())
                .user(user)
                .expirationDate(LocalDateTime.now().plusHours(12))
                .build();
        user.setToken(token);
        tokenRepository.save(token);
        return token;
    }
}
