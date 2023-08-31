package ru.nativespeaker.cloud_file_storage.file;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nativespeaker.cloud_file_storage.dto.ChangeFileNameRequest;
import ru.nativespeaker.cloud_file_storage.dto.FileNameSizeDto;

import java.util.List;

@RestController
public interface FileController {
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void uploadFile(@RequestParam("hash") String hash, @RequestParam("file") MultipartFile file, @RequestParam("filename") String fileName);
    @DeleteMapping(value = "/file")
    void deleteFile(@RequestParam("filename") String fileName);
    @GetMapping(value = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    MultiValueMap getFile(@RequestParam("filename") String fileName);
    @PutMapping("/file")
    void changeFileName(@RequestParam("filename") String fileName, @RequestBody ChangeFileNameRequest newFileName);
    @GetMapping("/list")
    List<FileNameSizeDto> getAvailableFileList(@RequestParam("limit") int limit);
    @RequestMapping(value = "/list", method = RequestMethod.OPTIONS)
    void listOptions(HttpServletResponse response);
}
