package com.mihirsingh.electronicstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mihirsingh.electronicstore.dtos.ApiResponseMessage;
import com.mihirsingh.electronicstore.dtos.UserDto;
import com.mihirsingh.electronicstore.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto user) {
        UserDto createUser = userService.createUser(user);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser( @Valid @RequestBody UserDto user, @PathVariable String id) {
        UserDto updateUser = userService.updaUserDto(user, id);
        return new ResponseEntity<UserDto>(updateUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String id) {
        userService.deleteUserDto(id);
        ApiResponseMessage message = ApiResponseMessage.builder().message("User Deleted Successfully").success(true)
                                    .status(HttpStatus.OK).build();
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String id) {
        UserDto singleUser = userService.getAUserById(id);
        return new ResponseEntity<UserDto>(singleUser, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> getAllUser = userService.getAllUser();
        return new ResponseEntity<List<UserDto>>(getAllUser, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getSingleUserByEmail(@PathVariable String email) {
        UserDto singleUser = userService.getUserByemail(email);
        return new ResponseEntity<UserDto>(singleUser, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        List<UserDto> searchUser = userService.searchUser(keyword);
        return new ResponseEntity<List<UserDto>>(searchUser, HttpStatus.OK);
    }

}
