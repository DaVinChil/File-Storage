package ru.nativespeaker.cloud_file_storage.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthorizationRequest;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthorizationResponse;

public interface AuthenticationController {
    @PostMapping("/login")
    AuthorizationResponse authenticate(@RequestBody AuthorizationRequest request);
    @RequestMapping(value = "/logout", method = RequestMethod.OPTIONS)
    void logoutOptions(HttpServletResponse response);
}
