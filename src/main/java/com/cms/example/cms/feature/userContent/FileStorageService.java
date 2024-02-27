package com.cms.example.cms.feature.userContent;

import com.cms.example.cms.entities.UserContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private UserContentRepository fileDBRepository;

    public UserContent store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        UserContent userContent = new UserContent(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(userContent);
    }

    public UserContent getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<UserContent> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
