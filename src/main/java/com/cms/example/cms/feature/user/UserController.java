package com.cms.example.cms.feature.user;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.CmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PutMapping(Routes.CMS_USER_UPDATE_BY_IDROUTE)
    public ResponseEntity<?> updateCmsUser(@PathVariable Long cmsUserId, @RequestBody CmsUser sourceUser) {
        try {
            CmsUser user = service.updateCmsUser(cmsUserId,sourceUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("UPDATE FAILED", HttpStatus.NOT_FOUND);
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
    @GetMapping(Routes.CMS_USER_LIST_ROUTE)
    public ResponseEntity<Map<String, Object>> getAllCmsUsers(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

            Page<CmsUser> pageTuts;
            if (name == null) {
                pageTuts = service.findAll(pageable);
            } else {
                pageTuts = service.findByNameContaining(name, pageable);
            }

            List<CmsUser> cmsUsers = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("cmsUsers", cmsUsers);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
