package com.cms.example.cms.feature.content;

import com.cms.example.cms.common.Routes;

import com.cms.example.cms.entities.Content;
import com.cms.example.cms.feature.content.responseDto.ContentDto;
import com.cms.example.cms.feature.content.responseDto.ContentUploadResponse;
import com.cms.example.cms.feature.content.responseDto.ResponseFile;
import com.cms.example.cms.feature.content.responseDto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService ;
    private final ContentRepository contentRepository ;

    @PostMapping(Routes.CONTENT_UPLOAD_ROUTE)
    public ResponseEntity<ResponseMessage> uploadContent(@RequestParam("cmsUserId") Long cmsUserId, @RequestParam("contents") MultipartFile[] files) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ContentUploadResponse> contentUploadResponseList = new ArrayList<>();
        for (MultipartFile file : files) {
            String response = contentService.uploadContentToFileSystem(file,cmsUserId);
            //contentUploadResponseList.add(response);
        }
        responseMessage.setResponseList(contentUploadResponseList);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

//    @GetMapping(Routes.CONTENT_BY_ID_ROUTE)
//    public ResponseEntity<?> getContent(@PathVariable Long contentId) {
//        Optional<Content> content = contentService.getContentWithUserById(contentId);
//
//        Content data = contentService.getFile(contentId);
//        String contentDownloadUri = ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("/content/")
//                .path(data.getContentId().toString())
//                .toUriString();
//
////        ContentDto contentDto = new ContentDto(content.get().getContentId(),content.get().getTitle(),content.get().getType(),
////                contentDownloadUri,content.get().getData().length,con)
//
//        return new ResponseEntity<>(content, HttpStatus.OK);
//    }
//
//    @GetMapping(Routes.CONTENT_LIST_ROUTE)
//    public ResponseEntity<List<ResponseFile>> getListContents( @RequestParam(required = false) String query,
//                                                               Pageable pageable) {
//
//        List<ResponseFile> files = contentService.getAllFiles().map(content -> {
//            String contentDownloadUri = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/content/")
//                    .path(content.getContentId().toString())
//                    .toUriString();
//
//            return new ResponseFile(
//                    content.getTitle(),
//                    contentDownloadUri,
//                    content.getType(),
//                    content.getData().length);
//        }).collect(Collectors.toList());
//
//        return new ResponseEntity<>(files, HttpStatus.OK);
//    }
//
    @GetMapping(Routes.CONTENT_DOWNLOAD_BY_ID_ROUTE)
    public ResponseEntity<byte[]> downloadContent(@PathVariable Long contentId) throws IOException {
        Optional<Content> contentData = contentRepository.findById(contentId);
        if (!contentData.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Content content = contentData.get();
        String filePath = content.getPath();
        byte[] contentBytes = Files.readAllBytes(new File(filePath).toPath());

        MediaType contentType = contentService.getContentTypeFromFileName(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setContentDispositionFormData("attachment", content.getTitle());

        return ResponseEntity.ok()
                .headers(headers)
                .body(contentBytes);
    }
}
