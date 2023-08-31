package ru.nativespeaker.cloud_file_storage.auth.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUuid(String uuid);
    boolean existsByUuid(String uuid);
}
