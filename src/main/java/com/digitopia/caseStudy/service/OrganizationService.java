package com.digitopia.caseStudy.service;

import com.digitopia.caseStudy.dto.OrganizationDto;
import com.digitopia.caseStudy.dto.UserDto;
import com.digitopia.caseStudy.entity.OrganizationEntity;
import com.digitopia.caseStudy.entity.UserEntity;
import com.digitopia.caseStudy.repository.OrganizationRepository;
import com.digitopia.caseStudy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationService {
    OrganizationRepository organizationRepository;
    ModelMapper modelMapper;
    private final UserRepository userRepository;

    @GetMapping
    public List<UserDto> getUsersForOrganization(Long registryNumber) {
       List<UserEntity> userEntities=organizationRepository.findByRegistryNumber(registryNumber).get().getUsers();
        return userEntities.stream().map(recordEntity -> modelMapper.map(userEntities, UserDto.class)).collect(Collectors.toList());
    }

    @GetMapping
    public List<UserDto> getOrganizations(String name,int year,int companySize) {
        Optional<OrganizationEntity> userEntities=organizationRepository.findByNormalizedNameContainingAndFoundedYearAndCompanySize(name,year,companySize);
        return userEntities.stream().map(recordEntity -> modelMapper.map(userEntities, UserDto.class)).collect(Collectors.toList());
    }



    public OrganizationDto createOrganization(OrganizationDto organizationDto, Long creatorId) {

        OrganizationEntity organizationEntity = modelMapper.map(organizationDto, OrganizationEntity.class);
        organizationEntity.setCreatedBy(creatorId);
        organizationEntity.setCreatedDate(new Date());
        organizationEntity.setUpdatedBy(0L);
        organizationEntity.setUpdatedDate(null);

       organizationEntity.setUsers(null);

        organizationEntity.setNormalizedName(normalizeName(organizationDto.getName()));

        return modelMapper.map(organizationRepository.save(organizationEntity), OrganizationDto.class);
    }

    private String normalizeName(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9]+", "");
    }


    public boolean deleteOrganization(Long organizationId) {
        try {
            organizationRepository.deleteById(organizationId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<OrganizationDto> getOrganizations() {
        List<OrganizationEntity> organizationEntities =  organizationRepository.findAll();

        return organizationEntities.stream()
                .map(organizationEntity -> modelMapper.map(organizationEntity, OrganizationDto.class))
                .collect(Collectors.toList());
    }

    public OrganizationEntity updateOrganization(Long organizationId,OrganizationEntity newOrganization,Long updatedId){

        Optional<OrganizationEntity> organization = organizationRepository.findById(organizationId);
        if(organization.isPresent()) {
            OrganizationEntity foundOrganization = organization.get();
            foundOrganization.setUsers(newOrganization.getUsers());
            foundOrganization.setCompanySize(newOrganization.getCompanySize());
            foundOrganization.setContactEmail(newOrganization.getContactEmail());
            foundOrganization.setName(newOrganization.getName());
            foundOrganization.setNormalizedName(newOrganization.getNormalizedName());
            foundOrganization.setPhone(newOrganization.getPhone());
            foundOrganization.setRegistryNumber(newOrganization.getRegistryNumber());
            foundOrganization.setFoundedYear(newOrganization.getFoundedYear());
            foundOrganization.setCreatedBy(newOrganization.getCreatedBy());
            foundOrganization.setCreatedDate(newOrganization.getCreatedDate());
            foundOrganization.setUpdatedBy(updatedId);
            foundOrganization.setUpdatedDate(new Date());
            foundOrganization.setId(newOrganization.getId());

            organizationRepository.save(foundOrganization);
            return foundOrganization;
        }else
            return null;

    }
}
