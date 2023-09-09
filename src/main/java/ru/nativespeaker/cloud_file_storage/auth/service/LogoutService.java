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
import ru.nativespeaker.cloud_file_storage.auth.user.User;
import ru.nativespeaker.cloud_file_storage.handler.exception.InternalServerException;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final AuthTokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(!authentication.isAuthenticated()) {
            throw new InternalServerException("Unauthenticated");
        }

        User user = (User) authentication.getPrincipal();

        AuthToken token = user.getToken();
        if(token != null){
            tokenRepository.delete(token);
            SecurityContextHolder.clearContext();
        } else {
            throw new InternalServerException("Could not find token");
        }
    }
}
