package ru.nativespeaker.cloud_file_storage.storage.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.storage.dto.ChangeFileNameRequest;

import org.springframework.http.HttpHeaders;
import ru.nativespeaker.cloud_file_storage.storage.dto.FileNameSizeDto;
import ru.nativespeaker.cloud_file_storage.storage.model.UserFile;
import ru.nativespeaker.cloud_file_storage.storage.service.FileService;
import ru.nativespeaker.cloud_file_storage.auth.user.User;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileControllerImp implements FileController {
    private final FileService fileService;

    @Override
    public void uploadFile(String hash, MultipartFile file, String fileName, Principal user) {
        fileService.uploadFile(hash, file, fileName, (User) user);
    }

    @Override
    public void deleteFile(String fileName, Principal user) {
        fileService.deleteFile(fileName, (User) user);
    }

    @Override
    public MultiValueMap<String, Object> getFile(String fileName, Principal user) {
        UserFile userFile = fileService.getFile(fileName, (User) user);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders contentTypeHeader = new HttpHeaders();
        if(userFile.getHash() != null && !userFile.getHash().isBlank()) {
            HttpEntity<String> hashPart = new HttpEntity<>(userFile.getHash());
            body.add("hash", hashPart);
        }
        if(userFile.getContent() != null && userFile.getContent().length != 0) {
            contentTypeHeader.setContentType(MediaType.valueOf(userFile.getFileType()));
            HttpEntity<String> filePart = new HttpEntity<>(new String(userFile.getContent(), StandardCharsets.UTF_8), contentTypeHeader);
            body.add("file", filePart);
        }
        return body;
    }

    @Override
    public void changeFileName(String fileName, ChangeFileNameRequest newFileName, Principal user) {
        fileService.changeFileName(fileName, newFileName.fileName(), (User) user);
    }

    @Override
    public List<FileNameSizeDto> getAvailableFileList(int limit, Principal user) {
        return convertListOfFilesToDto(fileService.getFirstNFiles(limit, (User) user));
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
}
