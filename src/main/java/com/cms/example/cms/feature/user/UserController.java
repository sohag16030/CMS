package com.cms.example.cms.feature.user;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.dto.GeoFilterDto;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.enums.EntityFetchType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping(Routes.CMS_USER_CREATE_ROUTE)
    public ResponseEntity<CmsUser> createProduct(@RequestBody CmsUser cmsUser) {
        CmsUser createdUser = service.saveCmsUser(cmsUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping(Routes.CMS_USER_UPDATE_ROUTE)
    public ResponseEntity<CmsUser> updateCmsUser( @RequestBody CmsUser updatedUser) {
        try {
            CmsUser user = service.updateCmsUser(updatedUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(Routes.CMS_USER_BY_ID_ROUTE)
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        CmsUser user = service.getCmsUserById(userId);
        if (Objects.nonNull(user)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            // TODO : throw EntityNotFoundException
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }
}
