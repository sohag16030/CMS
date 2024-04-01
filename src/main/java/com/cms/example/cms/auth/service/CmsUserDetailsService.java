//package com.cms.example.cms.auth.service;
//
//import com.cms.example.cms.entities.CmsUser;
//import com.cms.example.cms.feature.user.CmsUserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.security.Principal;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class CmsUserDetailsService implements UserDetailsService {
//
//    public static final String[] ADMIN_ACCESS = {"ROLE_ADMIN", "ROLE_MODERATOR"};
//    public static final String[] MODERATOR_ACCESS = {"ROLE_MODERATOR"};
//
//    private final CmsUserRepository repository;
//
//    public List<String> getRolesByLoggedInUser(Principal principal) {
//        String roles = getLoggedInUser(principal).getRoles();
//        List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
//        if (assignRoles.contains("ROLE_ADMIN")) {
//            return Arrays.stream(ADMIN_ACCESS).collect(Collectors.toList());
//        }
//        if (assignRoles.contains("ROLE_MODERATOR")) {
//            return Arrays.stream(MODERATOR_ACCESS).collect(Collectors.toList());
//        } else return Collections.emptyList();
//    }
//
//    private CmsUser getLoggedInUser(Principal principal) {
//        return repository.findByUserName(principal.getName()).get();
//    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<CmsUser> user = repository.findByUserName(username);
//        return user.map(CmsUserDetails::new)
//                .orElseThrow(()->new UsernameNotFoundException(username + " not exists"));
//    }
//}
