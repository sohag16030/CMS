package com.cms.example.cms.feature.user;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.CmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(Routes.CMS_USER_CREATE_ROUTE)
    public ResponseEntity<CmsUser> createProduct(@RequestBody CmsUser cmsUser) {
        CmsUser createdUser = userService.saveCmsUser(cmsUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping(Routes.CMS_USER_UPDATE_BY_IDROUTE)
    public ResponseEntity<?> updateCmsUser(@PathVariable Long cmsUserId, @RequestBody CmsUser sourceUser) {
        try {
            CmsUser user = userService.updateCmsUser(cmsUserId, sourceUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("UPDATE FAILED", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(Routes.CMS_USER_BY_ID_ROUTE)
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        CmsUser user = userService.getCmsUserById(userId);
        if (Objects.nonNull(user)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            // TODO : throw EntityNotFoundException
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(Routes.CMS_USER_LIST_ROUTE)
    public ResponseEntity getAllCmsUsers(
            @RequestParam(required = false) String query,
            Pageable pageable) {
        if (query == null || query.isEmpty()) {
            return ResponseEntity.ok(userService.getAllUsers(pageable));
        }
        return ResponseEntity.ok(userService.filterUsers(query, pageable));

    }
}
