package org.example.tour_guide.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tour_guide.dto.AuthRequest;
import org.example.tour_guide.dto.ChangePasswordRequest;
import org.example.tour_guide.dto.UserDTO;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.User;
import org.example.tour_guide.security.JwtService;
import org.example.tour_guide.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.ok("Registration successful. Please check your email for verification instructions.");
    }

    @GetMapping("/register")
    public ResponseEntity<String> confirmAgentAccount(@RequestParam("token") String token) {
        userService.verifyToken(token);
        return ResponseEntity.ok("Account verified successfully.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, Principal connectUser) {
        userService.changePassword(request,connectUser);
        return ResponseEntity.ok("Password was changed successfully");
    }


    @PostMapping("/agents")
    public ResponseEntity<Void> createUserForAgent(@RequestBody UserDTO request) {
        userService.createUserForAgent(request.getAgency(), request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/agency")
    public ResponseEntity<Void> addAgencyToUser(@RequestParam String username, @RequestBody Agency agency) {
        userService.addAgencyToUser(username, agency);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        log.info("invalid username " + request.username());
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }
}
