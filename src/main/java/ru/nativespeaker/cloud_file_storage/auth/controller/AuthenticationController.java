package ru.nativespeaker.cloud_file_storage.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationRequest;
import ru.nativespeaker.cloud_file_storage.auth.dto.AuthenticationResponse;

public interface AuthenticationController {
    @PostMapping("/login")
    AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request);
    @RequestMapping(value = "/logout", method = RequestMethod.OPTIONS)
    void logoutOptions(HttpServletResponse response);
}
