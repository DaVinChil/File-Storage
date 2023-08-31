package ru.nativespeaker.cloud_file_storage.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nativespeaker.cloud_file_storage.auth.token.Token;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(Token token);
    boolean existsByEmail(String email);
}
