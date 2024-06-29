package com.mihirsingh.electronicstore.services;


import java.util.List;

import com.mihirsingh.electronicstore.dtos.UserDto;

public interface UserService {
    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updaUserDto(UserDto userDto, String user_id);

    //delete
    void deleteUserDto(String user_id);

    //get all user
    List<UserDto> getAllUser();

    //get single user by id
    UserDto getAUserById(String user_id);

    //get single user by email
    UserDto getUserByemail(String email);

    //search user
    List<UserDto>   searchUser(String keyword);
    
    
}
