package ru.nativespeaker.cloud_file_storage.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.data_model.Token;
import ru.nativespeaker.cloud_file_storage.repository.TokenRepository;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("auth-token");
        final String token;
        if(authHeader == null){
            return;
        }

        token = authHeader.substring(7);
        Token storedToken = tokenRepository.findByUuid(token).orElse(null);
        if(storedToken != null){
            tokenRepository.delete(storedToken);
            SecurityContextHolder.clearContext();
        }
    }

}