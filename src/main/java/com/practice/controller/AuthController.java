package com.practice.controller;

import com.practice.entity.Role;
import com.practice.entity.User;
import com.practice.payload.LoginDto;
import com.practice.payload.SingUpDto;
import com.practice.repository.RoleRepository;
import com.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/singin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("User Login Successfully !",HttpStatus.OK);
    }


//http://localhost:8080/api/auth/singup

    @PreAuthorize("permitAll()")
    @PostMapping("/singup")
    public ResponseEntity<?> registerUser(@RequestBody SingUpDto singUpDto){
        if (userRepository.existsByUsername(singUpDto.getUsername())){
            return new ResponseEntity<>("Username Is Already Taken !..", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(singUpDto.getEmail())){
            return new ResponseEntity<>("Email Is Already In Use Try to Login",HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(singUpDto.getName());
        user.setEmail(singUpDto.getEmail());
        user.setUsername(singUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(singUpDto.getPassword()));

        Role role = roleRepository.findByName(singUpDto.getRoleType()).get();
        Set<Role> convertRoleToRoleSet = new HashSet<>();
        convertRoleToRoleSet.add(role);
        user.setRoles(convertRoleToRoleSet);

        userRepository.save(user);
        return new ResponseEntity<>("User Register Successfully !..",HttpStatus.CREATED);

    }

}
