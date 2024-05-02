package com.cms.example.cms.feature.address;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.dto.listDataFilterRequestDto.AddressFilter;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedAddressResponse;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedContentResponse;
import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.feature.user.CmsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AddressController {

    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final CmsUserRepository userRepository;

    @PostMapping(Routes.ADDRESS_CREATE_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> createAddress(@RequestBody Address address) {
        Address createdAddress = addressService.saveAddress(address.getCmsUser().getCmsUserId(), address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @PutMapping(Routes.ADDRESS_UPDATE_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody Address updatedAddress) {
        try {
            //need to pass the loggedIn user Id for save the address against the loggedIn user
            Address address = addressService.updateAddress(addressId, updatedAddress);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("UPDATE FAILED", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(Routes.ADDRESS_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        Address address = addressService.getAddressById(addressId);
        if (address == null) {
            return new ResponseEntity<>("ADDRESS NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @GetMapping(Routes.ADDRESS_LIST_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> getAllAddresses(AddressFilter filter, Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean userDetails) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CmsUser cmsUser = userRepository.getByUserName(username);
        String roles = authentication.getAuthorities().toString();
        PaginatedAddressResponse paginatedAddressResponse = null;
        if (roles.contains("ROLE_ADMIN")) {
            paginatedAddressResponse = addressService.getAllAddressesWithFilter(filter, pageable);
        } else {
            filter.setCmsUserId(cmsUser.getCmsUserId());
            paginatedAddressResponse = addressService.getAllAddressesWithFilter(filter, pageable);
        }

        if (paginatedAddressResponse == null) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paginatedAddressResponse, HttpStatus.OK);
    }

    @GetMapping(Routes.ADDRESS_LIST_ROUTE_FOR_USER_DETAILS)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> getAllAddresses(@PathVariable Long cmsUserId,AddressFilter filter, Pageable pageable) {

        PaginatedAddressResponse paginatedAddressResponse = null;

        filter.setCmsUserId(cmsUserId);
        paginatedAddressResponse = addressService.getAllAddressesWithFilter(filter, pageable);

        return new ResponseEntity<>(paginatedAddressResponse, HttpStatus.OK);
    }

    @DeleteMapping(Routes.ADDRESS_DELETE_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long addressId) {

        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            // Delete the address
            addressRepository.delete(address);
        } else {
            // Handle the case when the address does not exist
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete address. No records exists with Id :: " + addressId);
        }
        return null;
    }
}
