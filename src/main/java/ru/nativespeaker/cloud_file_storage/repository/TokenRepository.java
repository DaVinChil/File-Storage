package ru.nativespeaker.cloud_file_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nativespeaker.cloud_file_storage.data_model.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUuid(String uuid);
    boolean existsByUuid(String uuid);
}
