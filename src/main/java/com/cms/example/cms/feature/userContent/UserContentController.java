package com.cms.example.cms.feature.userContent;

import com.cms.example.cms.entities.UserContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserContentController {

    @Autowired
    private ContentUploadService storageService;

    @PostMapping("/uploadContents")
    public ResponseEntity<ResponseMessage> uploadMultipleFiles(@RequestParam("contents") MultipartFile[] files) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ContentUploadResponse> contentUploadResponseList = new ArrayList<>();
        for (MultipartFile file : files) {
            ContentUploadResponse response = storageService.uploadFile(file);
            contentUploadResponseList.add(response);
        }
        responseMessage.setResponseList(contentUploadResponseList);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/contents")
    public ResponseEntity<List<ResponseFile>> getListContents() {
        List<ResponseFile> files = storageService.getAllFiles().map(content -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(content.getUserContentId().toString())
                    .toUriString();

            return new ResponseFile(
                    content.getTitle(),
                    fileDownloadUri,
                    content.getType(),
                    content.getData().length);
        }).collect(Collectors.toList());

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<byte[]> getContent(@PathVariable Long id) {
        UserContent userContent = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; content_name=\"" + userContent.getTitle() + "\"")
                .body(userContent.getData());
    }
}
