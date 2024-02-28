package com.cms.example.cms.feature.userContent;

import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.UserContent;
import com.cms.example.cms.feature.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class ContentStorageService {

    @Autowired
    private UserContentRepository fileDBRepository;
    @Autowired
    private UserRepository userRepository;

    public ContentUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            store(file);
            return new ContentUploadResponse("SUCCESS",file.getOriginalFilename());
        } catch (Exception e) {
            return new ContentUploadResponse("FAILED",file.getOriginalFilename());
        }
    }
    public UserContent store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        UserContent userContent = new UserContent();
        userContent.setTitle(fileName);
        userContent.setType(file.getContentType());
        userContent.setData(file.getBytes());
        userContent.setIsActive(true);
        userContent.setCmsUser(userRepository.getOne(8L));

        return fileDBRepository.save(userContent);
    }

    public UserContent getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<UserContent> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
