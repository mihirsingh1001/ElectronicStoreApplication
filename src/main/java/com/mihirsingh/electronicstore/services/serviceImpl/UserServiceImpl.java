package com.mihirsingh.electronicstore.services.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mihirsingh.electronicstore.dtos.PageableResponse;
import com.mihirsingh.electronicstore.dtos.UserDto;
import com.mihirsingh.electronicstore.entities.User;
import com.mihirsingh.electronicstore.exceptions.ResourceNotFoundException;
import com.mihirsingh.electronicstore.repositories.UserRepository;
import com.mihirsingh.electronicstore.services.UserService;
import com.mihirsingh.electronicstore.utilites.Helper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${user.profile.image.path}")
    private String imagePath;

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
                .orElseThrow(() -> new ResourceNotFoundException("Error!! Given USER ID not found"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Error!! Given USER ID not found"));

        String fullPath = imagePath + user.getImage();
        Path path = Paths.get(fullPath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            logger.info("Exception :::: " + e);
        }
        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        return response;
    }

    @Override
    public UserDto getAUserById(String user_id) {
        User userId = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("Error!! Given USER ID not found"));
        UserDto userDtoId = entitiyToDto(userId);
        return userDtoId;
    }

    @Override
    public UserDto getUserByemail(String email) {
        // TODO Auto-generated method stub
        User userEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Error!! Given Email not found"));
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
        // .userid(savedUser.getUserid())
        // .name(savedUser.getName())
        // .password(savedUser.getPassword())
        // .email(savedUser.getEmail())
        // .about(savedUser.getAbout())
        // .image(savedUser.getImage())
        // .build();
        return mapper.map(savedUser, UserDto.class);

    }

    private User dtoToEntity(UserDto userDto) {

        // User user = User.builder()
        // .userid(userDto.getUserid())
        // .name(userDto.getName())
        // .password(userDto.getPassword())
        // .email(userDto.getEmail())
        // .about(userDto.getAbout())
        // .image(userDto.getImage())
        // .build();
        return mapper.map(userDto, User.class);

    }

}
