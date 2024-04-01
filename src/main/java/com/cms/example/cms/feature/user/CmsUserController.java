package com.cms.example.cms.feature.user;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedCmsUserResponse;
import com.cms.example.cms.dto.listDataFilterRequestDto.CmsUserFilter;
import com.cms.example.cms.entities.CmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CmsUserController {
    private final CmsUserService userService;
    private final CmsUserRepository userRepository;

    @PostMapping(Routes.CMS_USER_SIGN_UP_ROUTE)
    public ResponseEntity<CmsUser> createCmsUser(@RequestBody CmsUser cmsUser) {
        CmsUser response = userService.saveCmsUser(cmsUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(Routes.CMS_USER_UPDATE_BY_ID_ROUTE)
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> updateCmsUser(@PathVariable Long cmsUserId, @RequestBody CmsUser sourceUser, Principal principal) {
        if (userService.loggedInUser(principal, cmsUserId)) {
            try {
                CmsUser user = userService.updateCmsUser(cmsUserId, sourceUser);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("UPDATE FAILED", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(Routes.CMS_USER_BY_ID_ROUTE)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> getUserById(@PathVariable Long userId, Principal principal) {
        if (userService.loggedInUser(principal, userId) || userService.principalHasAdminRole(principal)) {
            CmsUser user = userService.getCmsUserById(userId);
            if (Objects.nonNull(user)) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                // TODO : throw EntityNotFoundException
                return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(Routes.CMS_USER_LIST_ROUTE)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<?> getAllCmsUsers(CmsUserFilter filter, Pageable pageable) {
        PaginatedCmsUserResponse paginatedCmsUserResponse = userService.getAllUsersWithFilter(filter, pageable);
        if (paginatedCmsUserResponse == null) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paginatedCmsUserResponse, HttpStatus.OK);

    }

    @DeleteMapping(Routes.CMS_USER_DELETE_BY_ID_ROUTE)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId, Principal principal) {
        if (userService.loggedInUser(principal, userId)) {
            try {
                userRepository.deleteById(userId);
                Optional<CmsUser> userOptional = userRepository.findById(userId);
                if (!userOptional.isPresent()) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user. No records exists with Id :: " + userId);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user. No records exists with Id :: " + userId);
    }
}
