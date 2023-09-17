package ru.nativespeaker.cloud_file_storage.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthToken;
import ru.nativespeaker.cloud_file_storage.auth.token.AuthTokenRepository;
import ru.nativespeaker.cloud_file_storage.handler.exception.InternalServerException;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final AuthTokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("auth-token");
        if(authHeader == null){
            throw new InternalServerException("Unauthenticated");
        }

        String token = authHeader.substring(7);
        AuthToken storedToken = tokenRepository.findByUuid(token).orElseThrow(() -> new InternalServerException("Could not find token"));
        if(storedToken != null){
            tokenRepository.delete(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
