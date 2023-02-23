package com.digitopia.caseStudy.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrganizationDto {
    private String name;
    private String registryNumber;
    private String contactEmail;
    private String phone;
    private int companySize;
    private int foundedYear;
    private List<Long> userIds;


}
