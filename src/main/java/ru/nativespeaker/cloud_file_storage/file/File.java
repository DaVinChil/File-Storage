package ru.nativespeaker.cloud_file_storage.data_model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import ru.nativespeaker.cloud_file_storage.user.User;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @NotNull
    @Column(name = "hash")
    private String hash;

    @NotNull
    @Column(name = "file_name")
    private String fileName;

    @NotNull
    @Lob
    @Column(name = "content", columnDefinition = "blob")
    private byte[] content;
}
