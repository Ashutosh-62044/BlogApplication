package com.blog.blogger.payload;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String email;
    private String password;
    private String username;
}
