package com.mihirsingh.electronicstore.services.serviceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mihirsingh.electronicstore.dtos.UserDto;
import com.mihirsingh.electronicstore.entities.User;
import com.mihirsingh.electronicstore.repositories.UserRepository;
import com.mihirsingh.electronicstore.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        // TODO Auto-generated method stub
        String userId = UUID.randomUUID().toString();
        userDto.setUserid(userId);

        User newUser = dtoToEntity(userDto);
        User user = userRepository.save(newUser);
        UserDto dtoUser = entitiyToDto(user);
        return dtoUser;
    }

    @Override
    public UserDto updaUserDto(UserDto userDto, String user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Error!! Given USER ID not found"));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getPassword());
        user.setImage(userDto.getImage());

        User updatedUser = userRepository.save(user);

        UserDto updatedUserDto = entitiyToDto(updatedUser);

        return updatedUserDto;
    }

    @Override
    public void deleteUserDto(String user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Error!! Given USER ID not found"));

        userRepository.delete(user);

    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtolist = users.stream().map(user -> entitiyToDto(user)).collect(Collectors.toList());
        return userDtolist;
    }

    @Override
    public UserDto getAUserById(String user_id) {
        User userId = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Error!! Given USER ID not found"));
        UserDto userDtoId = entitiyToDto(userId);
        return userDtoId;
    }

    @Override
    public UserDto getUserByemail(String email) {
        // TODO Auto-generated method stub
        User userEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error!! Given Email not found"));
        UserDto dtoUserEmail = entitiyToDto(userEmail);
        return dtoUserEmail;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        // TODO Auto-generated method stub
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> userDtolist = users.stream().map(user -> entitiyToDto(user)).collect(Collectors.toList());
        return userDtolist;
    }

    // EntityToDto
    private UserDto entitiyToDto(User savedUser) {

        // UserDto userDto = UserDto.builder()
        //         .userid(savedUser.getUserid())
        //         .name(savedUser.getName())
        //         .password(savedUser.getPassword())
        //         .email(savedUser.getEmail())
        //         .about(savedUser.getAbout())
        //         .image(savedUser.getImage())
        //         .build();
        return mapper.map(savedUser, UserDto.class);

    }

    private User dtoToEntity(UserDto userDto) {

        // User user = User.builder()
        //         .userid(userDto.getUserid())
        //         .name(userDto.getName())
        //         .password(userDto.getPassword())
        //         .email(userDto.getEmail())
        //         .about(userDto.getAbout())
        //         .image(userDto.getImage())
        //         .build();
        return mapper.map(userDto, User.class);

    }

}
