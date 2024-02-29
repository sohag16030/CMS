package com.cms.example.cms.feature.userContent;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.UserContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserContentController {

    @Autowired
    private ContentUploadService storageService;

    @PostMapping(Routes.USER_CONTENT_UPLOAD_ROUTE)
    public ResponseEntity<ResponseMessage> uploadMultipleFiles(@RequestParam("cmsUserId") Long cmsUserId, @RequestParam("contents") MultipartFile[] files) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ContentUploadResponse> contentUploadResponseList = new ArrayList<>();
        for (MultipartFile file : files) {
            ContentUploadResponse response = storageService.uploadFile(file,cmsUserId);
            contentUploadResponseList.add(response);
        }
        responseMessage.setResponseList(contentUploadResponseList);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping(Routes.USER_CONTENT_LIST_ROUTE)
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

    @GetMapping(Routes.USER_CONTENT_DOWNLOAD_BY_ID_ROUTE)
    public ResponseEntity<byte[]> getContent(@PathVariable Long id) {
        UserContent userContent = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; content_name=\"" + userContent.getTitle() + "\"")
                .body(userContent.getData());
    }
}
