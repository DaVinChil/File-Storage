package ru.nativespeaker.cloud_file_storage.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FileNameSizeDto {
    @JsonProperty("filename")
    private String fileName;
    private long size;
}
