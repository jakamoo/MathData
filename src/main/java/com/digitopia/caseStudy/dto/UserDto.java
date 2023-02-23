package com.digitopia.caseStudy.dto;

import com.digitopia.caseStudy.entity.OrganizationEntity;
import com.digitopia.caseStudy.status.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class UserDto{

    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String fullName;
    private String email;
    private List<Long> organizationIds;




}
