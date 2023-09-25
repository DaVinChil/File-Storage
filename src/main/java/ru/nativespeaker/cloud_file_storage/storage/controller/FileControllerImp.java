package ru.nativespeaker.cloud_file_storage.storage.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.storage.dto.ChangeFileNameRequest;

import org.springframework.http.HttpHeaders;
import ru.nativespeaker.cloud_file_storage.storage.dto.FileNameSizeDto;
import ru.nativespeaker.cloud_file_storage.storage.model.UserFile;
import ru.nativespeaker.cloud_file_storage.storage.service.FileService;
import ru.nativespeaker.cloud_file_storage.auth.user.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
@RestController
public class FileControllerImp implements FileController {
    private final Logger logger = LogManager.getLogger(FileControllerImp.class);
    private final FileService fileService;
    private final Level REQUEST_LEVEL = Level.forName("request", 1);

    @Override
    public void uploadFile(String hash, MultipartFile file, String fileName, Authentication auth) {
        User principal = (User) auth.getPrincipal();
        fileService.uploadFile(hash, file, fileName, principal);
        log(principal, RequestMethod.POST.name(), "/file", fileName);
    }

    @Override
    public void deleteFile(String fileName, Authentication auth) {
        User principal = (User) auth.getPrincipal();
        fileService.deleteFile(fileName, principal);
        log(principal, RequestMethod.DELETE.name(), "/file", fileName);
    }

    @Override
    public String getFile(String fileName, Authentication auth) {
        User principal = (User) auth.getPrincipal();
        UserFile userFile = fileService.getFile(fileName, principal);
        log(principal, RequestMethod.GET.name(), "/file", fileName);
        return new String(userFile.getContent() == null ? new byte[]{} : userFile.getContent(), StandardCharsets.UTF_8);
    }

    @Override
    public void changeFileName(String fileName, ChangeFileNameRequest newFileName, Authentication auth) {
        User principal = (User) auth.getPrincipal();
        fileService.changeFileName(fileName, newFileName.fileName(), principal);
        log(principal, RequestMethod.PUT.name(), "/file", fileName + " " + newFileName);
    }

    @Override
    public List<FileNameSizeDto> getAvailableFileList(int limit, Authentication auth) {
        User principal = (User) auth.getPrincipal();
        List<FileNameSizeDto> fileNameSizeDtos = convertListOfFilesToDto(fileService.getFirstNFiles(limit, principal));
        log(principal, RequestMethod.GET.name(), "/list", String.valueOf(limit));
        return fileNameSizeDtos;
    }

    @Override
    public void listOptions(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Methods", "GET");
    }

    private List<FileNameSizeDto> convertListOfFilesToDto(List<UserFile> userFiles) {
        List<FileNameSizeDto> result = new ArrayList<>(userFiles.size());
        for (UserFile userFile : userFiles) {
            result.add(new FileNameSizeDto(userFile.getFileName(), userFile.getSize()));
        }
        return result;
    }

    private void log(User user, String method, String endpoint, String msg) {
        logger.log(REQUEST_LEVEL, String.format("%30s %8s %20s %s SUCCESS", user.getEmail(), method, endpoint, msg));
    }
}
