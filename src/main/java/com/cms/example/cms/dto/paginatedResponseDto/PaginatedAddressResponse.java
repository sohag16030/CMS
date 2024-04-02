package com.cms.example.cms.dto.paginatedResponseDto;

import com.cms.example.cms.entities.Address;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedAddressResponse {
    private List<Address> addressList;
    private Long numberOfItems;
    private int numberOfPages;
}
