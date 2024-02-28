package com.cms.example.cms.feature.userContent;

import com.cms.example.cms.entities.UserContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserContentController {

    @Autowired
    private ContentStorageService storageService;

    @PostMapping("/uploadFiles")
    public ResponseEntity<ResponseMessage> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ContentUploadResponse> contentUploadResponseList = new ArrayList<>();
        for (MultipartFile file : files) {
            ContentUploadResponse response = storageService.uploadFile(file);
            contentUploadResponseList.add(response);
        }
        responseMessage.setResponseList(contentUploadResponseList);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getUserContentId().toString())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getTitle(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        UserContent fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getTitle() + "\"")
                .body(fileDB.getData());
    }
}
