package org.example.university.service;

import org.example.university.dto.UserRequest;
import org.example.university.exception.EmailOrPasswordInCorrectException;
import org.example.university.model.User;
import org.example.university.repository.UserRepository;
import org.example.university.security.JwtResponse;
import org.example.university.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<JwtResponse> login(UserRequest userRequest){
        User user=userRepository.findByEmail(userRequest.getEmail());
        if(user==null){
            throw  new EmailOrPasswordInCorrectException();
        }
        if (!passwordEncoder.matches(userRequest.getPassword(),user.getPassword())){
            throw  new EmailOrPasswordInCorrectException();
        }
        JwtResponse jwtResponse=new JwtResponse();
        jwtResponse.setJwtToken(jwtService.generateToken(user.getEmail()));
        jwtResponse.setEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }
}
