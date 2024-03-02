package com.cms.example.cms.auth.controller;

import com.cms.example.cms.auth.service.RefreshTokenService;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.feature.user.CmsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cms.example.cms.auth.service.CmsUserDetailsService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    public static final String DEFAULT_ROLE = "ROLE_USER";

    private final CmsUserDetailsService groupUserDetailsService;

    private final CmsUserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/joinGroup")
    public ResponseEntity<CmsUser> joinGroup(@RequestBody CmsUser user) {

        user.setRoles(DEFAULT_ROLE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/access/{userId}/{userRole}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public String giveAccessToUser(@PathVariable Long userId, @PathVariable String userRole, Principal principal) {

        CmsUser user = userRepository.findById(userId).get();
        List<String> activeRoles = groupUserDetailsService.getRolesByLoggedInUser(principal);

        String newRole = "";
        if (activeRoles.contains(userRole)) {
            newRole = user.getRoles() + "," + userRole;
            user.setRoles(newRole);
        }
        userRepository.save(user);
        return "Hi " + user.getUserName() + " new role assign to you by " + principal.getName();
    }
    @GetMapping("/loadUsers")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<CmsUser> loadUsers(){

        return userRepository.findAll();
    }
    @GetMapping("/showPersonalInfo")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public String userPersonalInfo() {
        return "Hi .this is my personal zone.";
    }


}
