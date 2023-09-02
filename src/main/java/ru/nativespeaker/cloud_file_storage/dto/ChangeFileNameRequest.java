package ru.nativespeaker.cloud_file_storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChangeFileNameRequest(@JsonProperty("filename") String fileName) {
}
