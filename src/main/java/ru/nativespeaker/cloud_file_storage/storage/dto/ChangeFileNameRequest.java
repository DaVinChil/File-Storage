package ru.nativespeaker.cloud_file_storage.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChangeFileNameRequest(@JsonProperty("filename") String fileName) {
}
