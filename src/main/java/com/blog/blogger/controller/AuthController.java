package com.blog.blogger.controller;

import com.blog.blogger.entity.Role;
import com.blog.blogger.entity.User;
import com.blog.blogger.payload.JWTAuthResponse;
import com.blog.blogger.payload.LoginDto;
import com.blog.blogger.payload.SignUpDto;
import com.blog.blogger.repository.RoleRepository;
import com.blog.blogger.repository.UserRepository;
import com.blog.blogger.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;




    // Login User
//     @PostMapping("/signIn")
//      public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        UsernamePasswordAuthenticationToken  usernamePasswordAuthenticationToken = new  UsernamePasswordAuthenticationToken(
//                  loginDto.getUsernameOrEmail(), loginDto.getPassword());
//
//        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//         SecurityContextHolder.getContext().setAuthentication(authentication);
//
//         return  new ResponseEntity<>("User signed-in successfully !. " , HttpStatus.OK);
//          }

    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }


    // Register User
          @PostMapping("/signUp")
          public ResponseEntity<?> createUser(@RequestBody SignUpDto signUpDto) {
            // Check if username is already taken
            if (userRepository.existsByUsername(signUpDto.getUsername())) {
                return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
            }

            // Check if email is already taken
            if (userRepository.existsByEmail(signUpDto.getEmail())) {
                return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            }

            // Create user object and save the user
            User user = new User();
            user.setName(signUpDto.getName());
            user.setEmail(signUpDto.getEmail());
            user.setUsername(signUpDto.getUsername());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

              Role roles = roleRepository.findByName("ROLE_ADMIN").get();
              user.setRoles(Collections.singleton(roles));

            User savedUser = userRepository.save(user);
           // System.out.println("Saved User: " + savedUser);

            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }
   }


// http://localhost:8080/swagger-ui/index.html#/  this url provides to frontend team.