package ru.nativespeaker.cloud_file_storage.storage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.storage.model.UserFile;
import ru.nativespeaker.cloud_file_storage.auth.user.User;

import java.util.List;
import java.util.Optional;

@Service
public interface FileService {
    UserFile uploadFile(String hash, MultipartFile file, String fileName, User user);
    Optional<UserFile> deleteFile(String fileName, User user);
    UserFile getFile(String fileName, User user);
    UserFile changeFileName(String fileName, String newFileName, User user);
    List<UserFile> getFirstNFiles(int limit, User user);
}
