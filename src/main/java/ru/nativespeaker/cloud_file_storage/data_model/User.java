package ru.nativespeaker.cloud_file_storage.data_model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(schema = "file_storage", name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;
}
