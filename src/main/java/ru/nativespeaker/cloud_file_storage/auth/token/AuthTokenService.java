package ru.nativespeaker.cloud_file_storage.auth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.user.User;
import ru.nativespeaker.cloud_file_storage.exception.InternalServerException;
import ru.nativespeaker.cloud_file_storage.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public boolean isExist(String token){
        return tokenRepository.existsByUuid(token);
    }

    public String getUserEmail(String token){
        return tokenRepository.findByUuid(token).map(userRepository::findByToken).map(opt -> opt.orElseThrow(() -> new InternalServerException("Cant find user by token"))).map(User::getEmail).orElse(null);
    }
}
