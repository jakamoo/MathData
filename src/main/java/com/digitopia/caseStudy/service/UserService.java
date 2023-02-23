package com.digitopia.caseStudy.service;

import com.digitopia.caseStudy.dto.OrganizationDto;
import com.digitopia.caseStudy.dto.UserDto;
import com.digitopia.caseStudy.entity.OrganizationEntity;
import com.digitopia.caseStudy.entity.UserEntity;
import com.digitopia.caseStudy.repository.OrganizationRepository;
import com.digitopia.caseStudy.repository.UserRepository;
import com.sun.jdi.InternalException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OrganizationRepository organizationRepository;


    public List<UserDto> getUsers(){

        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();

        return userEntities.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
                .collect(Collectors.toList());

    }

    public List<OrganizationDto> getOrganizations(Long id){

        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(userEntity.isPresent()){

            return userEntity.get().getOrganizations().stream().map(organizationEntity -> modelMapper.map(organizationEntity, OrganizationDto.class)).collect(Collectors.toList());
        }

        else
            return null;


    }


    public void createUser(UserDto userDto,Long creatorId) {

        try {
            UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

            userEntity.setCreatedBy(creatorId);
            userEntity.setCreatedDate(new Date());
            userEntity.setUpdatedBy(0L);
            userEntity.setUpdatedDate(null);
            userEntity.setNormalizedName(normalizeName(userEntity.getFullName()));
            userEntity.setOrganizations(null);


            List<Long> organizationIds = userDto.getOrganizationIds();
            List<OrganizationEntity> organizationEntities = organizationRepository.findAllById(organizationIds);
            userEntity.setOrganizations(organizationEntities);

            UserDto returnUserDto = modelMapper.map(userRepository.save(userEntity), UserDto.class);
            returnUserDto.setOrganizationIds(organizationIds);
        }
        catch (Exception e){

            throw new InternalException();
        }




    }







    private String normalizeName(String name) {
        return name.toLowerCase().replaceAll("[^a-z ]+", "");
    }


    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<UserDto> findUsersByNormalizedName(String normalizedName) {
        List<UserEntity> userEntities = userRepository.findAllByNormalizedName(normalizedName);
        return userEntities.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
                .collect(Collectors.toList());
    }

    public  UserDto getUserByEmail(String email) {
      Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        if(userEntity.isPresent()){

            return  modelMapper.map(userEntity, UserDto.class);
        }

        else
            throw new RuntimeException("User Not Found");
    }


    public void updateUser(Long creatorId, UserDto newUser, Long olUserId) {
        Optional<UserEntity> user = userRepository.findById(olUserId);


        if (user.isEmpty()){
            throw new RuntimeException("User Not Found");
        }

        else {

            UserEntity oldUser = user.get();
            oldUser.setFullName(newUser.getFullName());
            oldUser.setStatus(newUser.getStatus());
            oldUser.setNormalizedName(normalizeName(newUser.getFullName()));
            oldUser.setEmail(newUser.getEmail());
            oldUser.setUpdatedDate(new Date());
            oldUser.setUpdatedBy(creatorId);
            oldUser.setCreatedDate(new Date());
            oldUser.setCreatedBy(creatorId);
            userRepository.save(oldUser);
        }
    }
}

