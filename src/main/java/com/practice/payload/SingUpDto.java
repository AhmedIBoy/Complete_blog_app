package com.practice.payload;


import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingUpDto {
    private String name;
    private String email;
    private String username;
    private String password;
    private String roleType;
}
