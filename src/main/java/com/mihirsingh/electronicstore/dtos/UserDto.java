package com.mihirsingh.electronicstore.dtos;

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
    private String name;
    private String email;
    private String password;
    private String about;
    private String image;
}
