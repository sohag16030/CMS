package com.cms.example.cms.feature.address;

import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT u FROM Address u " +
            "JOIN FETCH u.division " +
            "JOIN FETCH u.district " +
            "JOIN FETCH u.upazila " +
            "WHERE u.addressId = :addressId")
    Optional<Address> fetchAddressInfoByAddressId(@Param("addressId") Long addressId);

    @Query(value = "SELECT addr FROM Address addr " +
            "JOIN FETCH addr.cmsUser cms " +
            "JOIN FETCH addr.division div " +
            "JOIN FETCH addr.district dis " +
            "JOIN FETCH addr.upazila upa " +
            "WHERE " +
            "(:searchText IS NULL OR " +
            "LOWER(div.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(dis.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(upa.name) LIKE LOWER(CONCAT('%', :searchText, '%')))",
            countQuery = "SELECT COUNT(addr) FROM Address addr")
    Page<Address> search(@Param("searchText") String searchText, Pageable pageable);

//    @Query(value = "SELECT DISTINCT con FROM Content con " +
//            "JOIN FETCH con.cmsUser cms " +
//            "WHERE " +
//            "(:title IS NULL OR " + // Check if searchValue is null
//            "LOWER(con.title) LIKE LOWER(CONCAT('%', :title, '%')) OR " + // Check title
//            "LOWER(con.type) LIKE LOWER(CONCAT('%', :title, '%')) OR " + // Check type
//            "LOWER(con.path) LIKE LOWER(CONCAT('%', :title, '%')) OR " + // Check path
//            "LOWER(cms.userName) LIKE LOWER(CONCAT('%', :title, '%')))", // Check userName
//            countQuery = "SELECT COUNT(con) FROM Content con " +
//                    "JOIN con.cmsUser cms ")
//    Page<Content> search(@Param("title") String title, Pageable pageable);




}
