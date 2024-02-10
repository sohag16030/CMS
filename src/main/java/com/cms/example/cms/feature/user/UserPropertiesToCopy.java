package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.UserRating;
import com.cms.example.cms.enums.Gender;
import com.cms.example.cms.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPropertiesToCopy {
    private String name;
    private Gender gender;
    private UserRating userRating;
    private UserStatus userStatus;
    private Boolean isActive;
}
