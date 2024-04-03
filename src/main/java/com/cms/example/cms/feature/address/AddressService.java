package com.cms.example.cms.feature.address;

import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.feature.geo.DistrictRepository;
import com.cms.example.cms.feature.geo.DivisionRepository;
import com.cms.example.cms.feature.geo.UpazilaRepository;
import com.cms.example.cms.feature.subject.SubjectRepository;
import com.cms.example.cms.feature.user.CmsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final CmsUserRepository userRepository;
    private final AddressRepository addressRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;

    @Transactional
    public List<Address> saveAddresses(List<Address> addresses) {
        List<Address> savedAddresses = new ArrayList<>();
        //Long loggedInUserId = getLoggedInUserId();
        for (Address address : addresses) {
            Address savedAddress = saveAddress(address.getCmsUser().getCmsUserId(), address);
            savedAddresses.add(savedAddress);
        }
        return savedAddresses;
    }

    @Transactional
    public Address saveAddress(Long loggedInUserId, Address address) {
        Address populatedAddress = populateAddress(loggedInUserId, address);
        return addressRepository.save(populatedAddress);
    }

    private Address populateAddress(Long loggedInUserId, Address address) {
        if (Division.isNonNull(address.getDivision())) {
            address.setDivision(divisionRepository.getOne(address.getDivision().getDivisionId()));
        }
        if (District.isNonNull(address.getDistrict())) {
            address.setDistrict(districtRepository.getOne(address.getDistrict().getDistrictId()));
        }

        if (Upazila.isNonNull(address.getUpazila())) {
            address.setUpazila(upazilaRepository.getOne(address.getUpazila().getUpazilaId()));
        }

        if (CmsUser.isNonNull(address.getCmsUser())) {
            address.setCmsUser(userRepository.getOne(loggedInUserId));
        }
        return address;
    }

    @Transactional
    public List<Address> updateAddress(Long loggedInUserId, List<Address> updatedAddressList) {

        List<Long> addressIds = updatedAddressList.stream().map(Address::getAddressId).collect(Collectors.toList());

        List<Address> getAddressesInfoByIds = addressRepository.fetchAddressesInfoByAddressIdsIn(addressIds);

        Map<Long, Address> addressesMap = getAddressesInfoByIds.stream()
                .collect(Collectors.toMap(Address::getAddressId, Function.identity()));

        List<Address> existingAddresses = addressRepository.findByCmsUserId(loggedInUserId);
        List<Long> existingAddressIds = existingAddresses.stream()
                .map(Address::getAddressId)
                .collect(Collectors.toList());

        for (Long existingAddressId : existingAddressIds) {
            if (!addressesMap.containsKey(existingAddressId)) {
                //delete it from db
                addressRepository.deleteById(existingAddressId);
            }
        }

        List<Address> updatedAddresses = new ArrayList<>();

        for (Address updatedAddressSource : updatedAddressList) {
            Long addressId = updatedAddressSource.getAddressId();
            if (addressesMap.containsKey(addressId)) {
                // modify existing addresses
                Address modifiedAddress = modifyExistingAddress(updatedAddressSource, addressId, addressesMap);
                updatedAddresses.add(modifiedAddress);
            } else {
                // new address should be added with existing addresses
                Address newAddress = populateAddress(updatedAddressSource.getCmsUser().getCmsUserId(), updatedAddressSource);
                updatedAddresses.add(newAddress);
                addressRepository.save(newAddress);
            }
        }
        return updatedAddresses;
    }

    private Address modifyExistingAddress(Address updatedAddressSource, Long
            addressId, Map<Long, Address> addressesMap) {
        Address modifiedAddress = addressesMap.get(addressId);
        //update address
        modifiedAddress.setAddressId(addressId);
        modifiedAddress.setAddressType(updatedAddressSource.getAddressType());
        if (updatedAddressSource.getDivision() != null) {
            modifiedAddress.setDivision(divisionRepository.getOne(updatedAddressSource.getDivision().getDivisionId()));
        }
        if (updatedAddressSource.getDistrict() != null) {
            modifiedAddress.setDistrict(districtRepository.getOne(updatedAddressSource.getDistrict().getDistrictId()));
        }
        if (updatedAddressSource.getUpazila() != null) {
            modifiedAddress.setUpazila(upazilaRepository.getOne(updatedAddressSource.getUpazila().getUpazilaId()));
        }
        modifiedAddress.setIsActive(updatedAddressSource.getIsActive());
        return modifiedAddress;
    }

    public Address getAddressById(Long addressId) {
        return null;
    }

    public void deleteAddressById(Long addressId) {
    }
}
