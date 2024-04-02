package com.cms.example.cms.feature.address;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AddressController {

    private final AddressService addressService;

    @PostMapping(Routes.ADDRESS_CREATE_ROUTE)
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address createdAddress = addressService.saveAddress(address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @PutMapping(Routes.ADDRESS_UPDATE_BY_ID_ROUTE)
    public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody Address updatedAddress) {
        try {
            Address address = addressService.updateAddress(addressId, updatedAddress);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("UPDATE FAILED", HttpStatus.NOT_FOUND);
        }
    }

    //    @GetMapping(Routes.ADDRESS_LIST_ROUTE) // Define the route for getting a list of addresses
//    public ResponseEntity<?> getAllAddresses(AddressFilter filter, Pageable pageable) {
//        PaginatedAddressResponse paginatedAddressResponse = addressService.getAllAddressesWithFilter(filter, pageable); // Call the service method to get addresses with pagination and filtering
//        if (paginatedAddressResponse == null) {
//            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND); // Return an error response if addresses not found
//        }
//        return new ResponseEntity<>(paginatedAddressResponse, HttpStatus.OK); // Return the paginated address response in the response body
//    }

    @GetMapping(Routes.ADDRESS_BY_ID_ROUTE)
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        Address address = addressService.getAddressById(addressId);
        if (Objects.nonNull(address)) {
            return new ResponseEntity<>(address, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
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
