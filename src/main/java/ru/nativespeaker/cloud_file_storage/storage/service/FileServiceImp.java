package ru.nativespeaker.cloud_file_storage.storage.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.handler.exception.NoSuchFileException;
import ru.nativespeaker.cloud_file_storage.storage.model.UserFile;
import ru.nativespeaker.cloud_file_storage.storage.repository.FileRepository;
import ru.nativespeaker.cloud_file_storage.auth.user.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {
    private final FileRepository fileRepository;

    @SneakyThrows(value = IOException.class)
    @Override
    public UserFile uploadFile(String hash, MultipartFile multipartFile, String fileName, User user) {
        UserFile userFileToStore = UserFile.builder()
                .fileType(multipartFile == null ? null : multipartFile.getContentType())
                .fileName(fileName)
                .content(multipartFile == null ? null : multipartFile.getBytes())
                .hash(hash)
                .user(user).build();
        return fileRepository.save(userFileToStore);
    }

    @Override
    @Transactional
    public Optional<UserFile> deleteFile(String fileName, User user) {
        return fileRepository.deleteByFileNameAndUser_Id(fileName, user.getId());
    }

    @Override
    public UserFile getFile(String fileName, User user) {
        return fileRepository.findByFileNameAndUser_Id(fileName, user.getId())
                .orElseThrow(() -> new NoSuchFileException("Wrong file name or no file with such name."));
    }

    @Override
    public UserFile changeFileName(String fileName, String newFileName, User user) {
        UserFile userFile = fileRepository.findByFileNameAndUser_Id(fileName, user.getId())
                .orElseThrow(() -> new NoSuchFileException("Wrong file name or no file with such name."));
        userFile.setFileName(newFileName);
        return fileRepository.save(userFile);
    }

    @Override
    public List<UserFile> getFirstNFiles(int limit, User user) {
        return fileRepository.findFirstNFiles(limit, user.getId());
    }
}
