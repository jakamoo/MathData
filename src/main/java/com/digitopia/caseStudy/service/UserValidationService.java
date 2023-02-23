package com.digitopia.caseStudy.service;

import com.digitopia.caseStudy.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
 public class  UserValidationService {


    public static boolean isNameValid(String name) {
        return name.matches("^[a-zA-Z]+$");
    }
    public static boolean  isEmailValid(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Z|a-z]{2,}$");
    }
}