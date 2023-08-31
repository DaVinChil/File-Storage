package ru.nativespeaker.cloud_file_storage.file;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.dto.ChangeFileNameRequest;

import org.springframework.http.HttpHeaders;
import ru.nativespeaker.cloud_file_storage.dto.FileNameSizeDto;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileControllerImp implements FileController {
    private final FileService fileService;

    @Override
    public void uploadFile(String hash, MultipartFile file, String fileName) {
        fileService.uploadFile(hash, file, fileName);
    }

    @Override
    public void deleteFile(String fileName) {
        fileService.deleteFile(fileName);
    }

    @Override
    public MultiValueMap<String, Object> getFile(String fileName) {
        File file = fileService.getFile(fileName);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders contentTypeHeader = new HttpHeaders();
        HttpEntity<String> hashPart = new HttpEntity<>(file.getHash());
        body.add("hash", hashPart);
        contentTypeHeader.setContentType(MediaType.valueOf(file.getFileType()));
        HttpEntity<String> filePart = new HttpEntity<>(new String(file.getContent(), StandardCharsets.UTF_8), contentTypeHeader);
        body.add("file", filePart);
        return body;
    }

    @Override
    public void changeFileName(String fileName, ChangeFileNameRequest newFileName) {
        fileService.changeFileName(fileName, newFileName.name());
    }

    @Override
    public List<FileNameSizeDto> getAvailableFileList(int limit) {
        return convertListOfFilesToDto(fileService.getFirstNFiles(limit));
    }

    @Override
    public void listOptions(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Methods", "GET");
    }

    public List<FileNameSizeDto> convertListOfFilesToDto(List<File> files) {
        List<FileNameSizeDto> result = new ArrayList<>(files.size());
        for (int i = 0; i < files.size(); i++) {
            result.add(new FileNameSizeDto(files.get(i).getFileName(), files.get(i).getSize()));
        }
        return result;
    }
}
