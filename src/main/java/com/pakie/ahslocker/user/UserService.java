package com.pakie.ahslocker.user;

import com.pakie.ahslocker.web.UserDto;
import com.pakie.ahslocker.web.UserRegistrationDto;

public interface UserService {
    User findUserByUsername(String username);
    void encodePassword(UserDto source, User target);
    User register(UserRegistrationDto registrationDto);
}
