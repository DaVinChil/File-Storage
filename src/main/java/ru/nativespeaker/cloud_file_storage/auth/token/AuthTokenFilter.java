package ru.nativespeaker.cloud_file_storage.auth.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.nativespeaker.cloud_file_storage.user.User;
import ru.nativespeaker.cloud_file_storage.user.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final AuthTokenService tokenService;
    private final UserDetailsService userDetailsService;

    public AuthTokenFilter(UserRepository userRepository, AuthTokenService tokenService, @Lazy UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("auth-token");
        final String token;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);
        if(!tokenService.isValidOrDelete(token)) {
            filterChain.doFilter(request,response);
            return;
        }
        Optional<User> user = userRepository.findByToken_Uuid(token);
        if (user.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getUsername());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    null
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
