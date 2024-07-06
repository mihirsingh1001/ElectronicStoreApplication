package com.mihirsingh.electronicstore.dtos;

import com.mihirsingh.electronicstore.utilites.validate.ImageNameValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userid;

    @Size(min = 3, max = 15)
    private String name;

    @Email(message = "Invalid email !!")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Size(min = 3, max = 15, message = "Password is required")
    @NotBlank
    private String password;

    @NotBlank(message = "Write about something on yourself")
    private String about;

    @ImageNameValid
    private String image;
}
