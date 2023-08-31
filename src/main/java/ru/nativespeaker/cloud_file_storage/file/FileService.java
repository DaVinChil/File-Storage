package ru.nativespeaker.cloud_file_storage.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileService {
    void uploadFile(String hash, MultipartFile file, String fileName);
    void deleteFile(String fileName);
    File getFile(String fileName);
    void changeFileName(String fileName, String newFileName);
    List<File> getFirstNFiles(int limit);
}
