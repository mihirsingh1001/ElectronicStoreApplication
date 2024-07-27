package com.mihirsingh.electronicstore.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mihirsingh.electronicstore.dtos.ApiResponseMessage;
import com.mihirsingh.electronicstore.dtos.ImageResponse;
import com.mihirsingh.electronicstore.dtos.PageableResponse;
import com.mihirsingh.electronicstore.dtos.UserDto;
import com.mihirsingh.electronicstore.services.FileService;
import com.mihirsingh.electronicstore.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        UserDto createUser = userService.createUser(user);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto user, @PathVariable String id) {
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
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<UserDto> getAllUser = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PageableResponse<UserDto>>(getAllUser, HttpStatus.OK);
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
    };

    @PostMapping("/image/{userid}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("imageName") MultipartFile image,
            @PathVariable String userid) {
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto user = userService.getAUserById(userid);
        user.setImage(imageName);
        UserDto userDto = userService.updaUserDto(user, userid);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true)
                .status(HttpStatus.CREATED).build();
        return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{userid}")
    public void serveUserImage(@PathVariable String userid, HttpServletResponse response) throws IOException {
        UserDto user = userService.getAUserById(userid);
        logger.info("User Image name ::: " + user.getImage());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImage());

        response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
