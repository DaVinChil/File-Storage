package ru.nativespeaker.cloud_file_storage.file;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.exception.NoSuchFileException;
import ru.nativespeaker.cloud_file_storage.user.User;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {
    private final FileRepository fileRepository;

    @SneakyThrows(value = IOException.class)
    @Override
    public void uploadFile(String hash, MultipartFile multipartFile, String fileName, User user) {
        File fileToStore = File.builder()
                .fileType(multipartFile == null ? null : multipartFile.getContentType())
                .fileName(fileName)
                .content(multipartFile == null ? null : multipartFile.getBytes())
                .hash(hash)
                .user(user).build();
        fileRepository.save(fileToStore);
    }

    @Override
    @Transactional
    public void deleteFile(String fileName, User user) {
        fileRepository.deleteByFileNameAndUser_Id(fileName, user.getId());
    }

    @Override
    public File getFile(String fileName, User user) {
        return fileRepository.findByFileNameAndUser_Id(fileName, user.getId())
                .orElseThrow(() -> new NoSuchFileException("Wrong file name or no file with such name."));
    }

    @Override
    public void changeFileName(String fileName, String newFileName, User user) {
        File file = fileRepository.findByFileNameAndUser_Id(fileName, user.getId())
                .orElseThrow(() -> new NoSuchFileException("Wrong file name or no file with such name."));
        file.setFileName(newFileName);
        fileRepository.save(file);
    }

    @Override
    public List<File> getFirstNFiles(int limit, User user) {
        return fileRepository.findFirstNFiles(limit, user.getId());
    }
}
