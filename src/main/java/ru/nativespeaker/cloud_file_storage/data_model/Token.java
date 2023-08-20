package ru.nativespeaker.cloud_file_storage.data_model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
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

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    protected Token() {

    }
}
