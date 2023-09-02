package ru.nativespeaker.cloud_file_storage.file;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.nativespeaker.cloud_file_storage.user.User;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "hash")
    private String hash;

    @NotNull
    @Column(name = "file_name")
    private String fileName;
    @NotNull
    @Column(name = "file_type")
    private String fileType;

    @Lob
    @Column(name = "content", columnDefinition = "blob")
    private byte[] content;

    @NotNull
    @Column(name = "size")
    private long size;
}
