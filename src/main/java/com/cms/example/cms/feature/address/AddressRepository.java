package com.cms.example.cms.feature.address;

import com.cms.example.cms.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT addr FROM Address addr " +
            "WHERE " +
            "(:divisionName IS NULL OR addr.division.name LIKE CONCAT('%', :divisionName, '%')) AND " +
            "(:districtName IS NULL OR addr.district.name LIKE CONCAT('%', :districtName, '%')) AND " +
            "(:upazilaName IS NULL OR addr.upazila.name LIKE CONCAT('%', :upazilaName, '%')) AND " +
            "(:isActive IS NULL OR addr.isActive = :isActive)")
    Page<Address> search(@Param("divisionName") String divisionName,
                         @Param("districtName") String districtName,
                         @Param("upazilaName") String upazilaName,
                         @Param("isActive") Boolean isActive,
                         Pageable pageable);
}
