package ru.nativespeaker.cloud_file_storage.storage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.nativespeaker.cloud_file_storage.auth.user.User;
import ru.nativespeaker.cloud_file_storage.auth.user.UserRepository;
import ru.nativespeaker.cloud_file_storage.storage.model.UserFile;
import ru.nativespeaker.cloud_file_storage.storage.repository.FileRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FileRepositoryTest {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Autowired
    public FileRepositoryTest(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void findNFirstFiles() {
        User user = User.builder()
                .id(1)
                .email("fox")
                .password("dkla")
                .build();
        userRepository.save(user);

        for (int i = 0; i < 3; i++) {
            fileRepository.save(
                    UserFile.builder()
                            .fileName("file" + i + ".txt")
                            .fileType("plane/text")
                            .hash("hash" + i)
                            .size(0)
                            .content(null)
                            .user(user).build()
            );
        }

        int limit = 3;

        List<UserFile> result = fileRepository.findFirstNFiles(limit, user.getId());

        assertEquals(limit, result.size());
        result.forEach(item -> assertEquals(user.getId(), item.getUser().getId()));
    }
}
