package com.cms.example.cms.feature.address;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.dto.listDataFilterRequestDto.AddressFilter;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedAddressResponse;
import com.cms.example.cms.entities.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AddressController {

    private final AddressService addressService;

    @PostMapping(Routes.ADDRESS_CREATE_ROUTE)
    public ResponseEntity<?> createAddress(@RequestBody Address address) {
        Address createdAddress = addressService.saveAddress(address.getCmsUser().getCmsUserId(), address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @PutMapping(Routes.ADDRESS_UPDATE_BY_ID_ROUTE)
    public ResponseEntity<?> updateAddress(@PathVariable Long userId, @RequestBody Address updatedAddress) {
        try {
            //need to pass the loggedIn user Id for save the address against the loggedIn user
            Address address = addressService.updateAddress(userId,updatedAddress);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("UPDATE FAILED", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(Routes.ADDRESS_BY_ID_ROUTE) // Define the route for getting an address by ID
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        Address address = addressService.getAddressById(addressId);
        if (address == null) {
            return new ResponseEntity<>("ADDRESS NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @GetMapping(Routes.ADDRESS_LIST_ROUTE) // Define the route for getting a list of addresses
    public ResponseEntity<?> getAllAddresses(AddressFilter filter, Pageable pageable) {
        PaginatedAddressResponse paginatedAddressResponse = addressService.getAllAddressesWithFilter(filter, pageable);
        if (paginatedAddressResponse == null) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paginatedAddressResponse, HttpStatus.OK);
    }

    @DeleteMapping(Routes.ADDRESS_DELETE_BY_ID_ROUTE)
    public ResponseEntity<?> deleteAddressById(@PathVariable Long addressId) {
        try {
            addressService.deleteAddressById(addressId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete address. No records exists with Id :: " + addressId);
        }
    }
}
