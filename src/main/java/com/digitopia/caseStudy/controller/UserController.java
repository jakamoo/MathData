package com.digitopia.caseStudy.controller;

import com.digitopia.caseStudy.dto.OrganizationDto;
import com.digitopia.caseStudy.dto.UserDto;
import com.digitopia.caseStudy.entity.UserEntity;
import com.digitopia.caseStudy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> users = userService.getUsers();

        if(users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/{id}/getOrganizations")
    public ResponseEntity<List<OrganizationDto>> getAllOrganization(@PathVariable Long id) {
        List<OrganizationDto> organizations = userService.getOrganizations(id);

       if(organizations.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(organizations,HttpStatus.OK);


    }

    @PutMapping("/update/{oldUserId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long oldUserId,
                                           @RequestBody UserDto userDto,
                                           @RequestParam Long creatorId) {

       userService.updateUser(creatorId, userDto,oldUserId);


       return new ResponseEntity<>(HttpStatus.OK);


    }



    @PostMapping("/create/{creatorId}")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto,@PathVariable Long creatorId) {

        userService.createUser(userDto,creatorId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/searchByNormalizedName/{normalizedName}")
    public ResponseEntity<List<UserDto>> findUsersByNormalizedName(@PathVariable String normalizedName) {
        List<UserDto> users = userService.findUsersByNormalizedName(normalizedName);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/searchByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);

        if(user!=null)
            return new ResponseEntity<>(user,HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
