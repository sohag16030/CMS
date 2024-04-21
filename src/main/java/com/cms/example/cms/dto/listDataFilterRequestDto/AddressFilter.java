package com.cms.example.cms.dto.listDataFilterRequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressFilter {
    private Long cmsUserId;
    private String divisionName;
    private String districtName;
    private String upazilaName;
    private Boolean isActive;
}

