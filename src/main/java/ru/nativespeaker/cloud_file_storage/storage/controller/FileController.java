package ru.nativespeaker.cloud_file_storage.storage.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.storage.dto.ChangeFileNameRequest;
import ru.nativespeaker.cloud_file_storage.storage.dto.FileNameSizeDto;

import java.security.Principal;
import java.util.List;

@RestController
public interface FileController {
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void uploadFile(@RequestParam(value = "hash", required = false) String hash,
                    @RequestParam(value = "file", required = false) MultipartFile file,
                    @RequestParam("filename") String fileName,
                    Authentication auth);

    @DeleteMapping(value = "/file")
    void deleteFile(@RequestParam("filename") String fileName,
                    Authentication auth);

    @GetMapping(value = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    byte[] getFile(@RequestParam("filename") String fileName,
                                          Authentication auth);

    @PutMapping("/file")
    void changeFileName(@RequestParam("filename") String fileName,
                        @RequestBody ChangeFileNameRequest newFileName,
                        Authentication auth);

    @GetMapping("/list")
    List<FileNameSizeDto> getAvailableFileList(@RequestParam("limit") int limit,
                                               Authentication auth);

    @RequestMapping(value = "/list", method = RequestMethod.OPTIONS)
    void listOptions(HttpServletResponse response);
}
