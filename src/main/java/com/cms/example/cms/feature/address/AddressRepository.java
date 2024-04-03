package com.cms.example.cms.feature.address;

import com.cms.example.cms.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a  WHERE a.addressId IN :addressIds")
    List<Address> fetchAddressesInfoByAddressIdsIn(@Param("addressIds") List<Long> addressIds);

    @Query("SELECT a FROM Address a WHERE a.cmsUser.cmsUserId = :cmsUserId")
    List<Address> findByCmsUserId(@Param("cmsUserId") Long cmsUserId);
}
