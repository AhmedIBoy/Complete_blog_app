package com.practice.security;

import com.practice.entity.Role;
import com.practice.entity.User;
import com.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("UserName Or Email Not Found : " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getUsername(),mapRoleAuthority(user.getRoles()));
    }

    private Collection< ? extends GrantedAuthority> mapRoleAuthority(Set<Role> role){
        return role.stream().map((r) -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
