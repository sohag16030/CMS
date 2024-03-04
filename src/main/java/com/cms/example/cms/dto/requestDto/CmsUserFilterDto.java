package com.cms.example.cms.dto.requestDto;
import com.cms.example.cms.enums.Gender;
import com.cms.example.cms.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserFilterDto {
    private Long cmsUserId;
    private String userName;
    private String roles;
    private String mobileNumber;
    private String email;
    private String name;
    private String gender;
    private String userStatus;
    private Boolean isActive;
}
