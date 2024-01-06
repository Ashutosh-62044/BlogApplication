package com.blog.blogger.security;

import com.blog.blogger.entity.Role;
import com.blog.blogger.entity.User;
import com.blog.blogger.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

      private UserRepository userRepository;

       public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
       }

       // Searching the record in database based on Email or Username...
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
               .orElseThrow(()->
                       new UsernameNotFoundException("User not found with username and Email " +usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {

           return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                   .collect(Collectors.toList());
       }
}
