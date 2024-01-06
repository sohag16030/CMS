package com.cms.example.cms.feature.user;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.CmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final CmsUserService service;

    @PostMapping(Routes.CMS_USER_CREATE_ROUTE)
    public ResponseEntity<CmsUser> createProduct(@RequestBody CmsUser cmsUser) {
        CmsUser createdProduct = service.saveCmsUser(cmsUser);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
}
