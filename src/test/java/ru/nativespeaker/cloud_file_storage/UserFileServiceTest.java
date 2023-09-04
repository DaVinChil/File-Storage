package ru.nativespeaker.cloud_file_storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.auth.user.User;
import ru.nativespeaker.cloud_file_storage.handler.exception.NoSuchFileException;
import ru.nativespeaker.cloud_file_storage.storage.model.UserFile;
import ru.nativespeaker.cloud_file_storage.storage.repository.FileRepository;
import ru.nativespeaker.cloud_file_storage.storage.service.FileServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserFileServiceTest {
    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileServiceImp userFileService;

    @Test
    public void uploadFile_shouldReturnTheSame() {
        when(fileRepository.save(any())).then(inv -> inv.getArgument(0));

        String fileName = "file.txt";
        String contentType = "plane/text";
        byte[] content = "Hello, world!".getBytes();
        MultipartFile file = new MockMultipartFile(fileName, fileName,
                contentType, content);
        String hash = "98d3-dfal213-bzhyw";
        User user = User.builder()
                .email("fox")
                .build();

        UserFile savedFile = userFileService.uploadFile(hash, file, fileName, user);

        assertEquals(fileName, savedFile.getFileName());
        assertEquals(content, savedFile.getContent());
        assertEquals(contentType, savedFile.getFileType());
        assertEquals(hash, savedFile.getHash());
        assertEquals(user.getEmail(), savedFile.getUser().getEmail());
    }

    @Test
    public void deleteFile_shouldReturnTheSame() {
        when(fileRepository.deleteByFileNameAndUser_Id(any(), any()))
                .then(inv ->
                        Optional.of(
                                UserFile.builder()
                                        .fileName(inv.getArgument(0))
                                        .user(User.builder()
                                                .id(inv.getArgument(1))
                                                .build()
                                        )
                                        .build()
                        )
                );

        String fileName = "file.txt";
        User user = User.builder()
                .id(11L)
                .build();

        UserFile deletedFile = userFileService.deleteFile(fileName, user).orElseThrow();

        assertEquals(fileName, deletedFile.getFileName());
        assertEquals(user.getId(), deletedFile.getUser().getId());
    }

    @Test
    public void getFile_shouldReturnTheSame() {
        when(fileRepository.findByFileNameAndUser_Id(any(), any()))
                .then(inv ->
                        Optional.of(
                                UserFile.builder()
                                        .fileName(inv.getArgument(0))
                                        .user(User.builder()
                                                .id(inv.getArgument(1))
                                                .build()
                                        )
                                        .build()
                        )
                );

        String fileName = "file.txt";
        User user = User.builder()
                .id(11L)
                .build();

        UserFile savedFile = userFileService.getFile(fileName, user);

        assertEquals(fileName, savedFile.getFileName());
        assertEquals(user.getId(), savedFile.getUser().getId());
    }

    @Test
    public void getFile_shouldThrow() {
        when(fileRepository.findByFileNameAndUser_Id(any(), any())).thenReturn(Optional.empty());

        String fileName = "file.txt";
        User user = User.builder()
                .id(11L)
                .build();

        assertThrows(NoSuchFileException.class, () -> userFileService.getFile(fileName, user));
    }

    @Test
    public void changeFile_shouldChange() {
        when(fileRepository.findByFileNameAndUser_Id(any(), any()))
                .then(inv ->
                        Optional.of(
                                UserFile.builder()
                                        .fileName(inv.getArgument(0))
                                        .user(User.builder()
                                                .id(inv.getArgument(1))
                                                .build()
                                        )
                                        .build()
                        )
                );
        when(fileRepository.save(any())).thenAnswer((inv) -> inv.getArgument(0));

        String fileName = "file.txt";
        String newFileName = "newFile.txt";
        User user = User.builder()
                .id(11L)
                .build();

        UserFile changedFile = userFileService.changeFileName(fileName, newFileName, user);

        assertEquals(newFileName, changedFile.getFileName());
        assertEquals(user.getId(), changedFile.getUser().getId());
    }

    @Test
    public void changeFile_shouldThrow() {
        when(fileRepository.findByFileNameAndUser_Id(any(), any())).thenReturn(Optional.empty());

        String fileName = "file.txt";
        String newFileName = "newFile.txt";
        User user = User.builder()
                .id(11L)
                .build();

        assertThrows(NoSuchFileException.class, () -> userFileService.changeFileName(fileName, newFileName, user));
    }

    @Test
    public void getFirstNFiles() {
        when(fileRepository.findFirstNFiles(anyInt(), any())).thenAnswer(
                inv -> {
                    List<UserFile> list = new ArrayList<>((int) inv.getArgument(0));
                    for (int i = 0; i < (int) inv.getArgument(0); i++) {
                        list.add(new UserFile());
                    }
                    return list;
                }
        );

        int limit = 3;

        List<UserFile> result = userFileService.getFirstNFiles(limit, User.builder().id(1).build());
        assertEquals(limit, result.size());
    }
}
