package ru.nativespeaker.cloud_file_storage.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> deleteByFileNameAndUser_Id(String fileName, Long userId);
    Optional<File> findByFileNameAndUser_Id(String fileName, Long userId);
    @Query(value = "select * from files where user_id = :user_id limit :limit",
    nativeQuery = true)
    List<File> findFirstNFiles(@Param("limit") int limit, @Param("user_id") Long userId);
}
