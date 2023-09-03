package ru.nativespeaker.cloud_file_storage.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.user.User;

import java.util.List;

@Service
public interface FileService {
    void uploadFile(String hash, MultipartFile file, String fileName, User user);
    void deleteFile(String fileName, User user);
    File getFile(String fileName, User user);
    void changeFileName(String fileName, String newFileName, User user);
    List<File> getFirstNFiles(int limit, User user);
}
