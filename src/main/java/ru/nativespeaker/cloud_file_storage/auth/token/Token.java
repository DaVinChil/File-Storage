package ru.nativespeaker.cloud_file_storage.auth.token;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private long id;

    @NotNull
    @Column(name = "uuid", unique = true)
    private String uuid;

    @NotNull
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
}
