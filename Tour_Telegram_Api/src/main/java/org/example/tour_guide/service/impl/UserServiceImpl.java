package org.example.tour_guide.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.example.tour_guide.dto.ChangePasswordRequest;
import org.example.tour_guide.dto.UserDTO;
import org.example.tour_guide.exceptions.TokenInvalidException;
import org.example.tour_guide.exceptions.UserAlreadyExistsException;
import org.example.tour_guide.exceptions.WrongPasswordException;
import org.example.tour_guide.models.*;
import org.example.tour_guide.repository.ConfirmationRepository;
import org.example.tour_guide.repository.UserRepository;

import org.example.tour_guide.service.EmailService;
import org.example.tour_guide.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void createUserForAgent(Agency agency, String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAgency(agency);
        userRepository.save(user);


    }

    @Override
    public List<User> getActiveUsers() {
        return userRepository.getActiveUsers();
    }

    @Override
    public void registerUser(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists");
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new RuntimeException("Password and confirm password are different");
        }

        User newUser = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .authorities(userDTO.getAuthorities())
                .isActive(false)
                .build();

        userRepository.save(newUser);
        Confirmation confirmation = new Confirmation(newUser);
        confirmationRepository.save(confirmation);
        emailService.sentMailMessage(newUser.getUsername(), newUser.getEmail(), confirmation.getToken());
    }

    @Override
    public void verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token)
                .orElseThrow(() -> new TokenInvalidException("Token is invalid."));

        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail())
                .orElseThrow(() -> new TokenInvalidException("User not found for token"));

        user.setActive(true);
        userRepository.save(user);

        confirmationRepository.delete(confirmation);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

    }

    @Override
    public Optional<User> getByUserName(String username) {
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void addAgencyToUser(String username, Agency agency) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!agency.getUser().contains(user) && user.isActive()) {
            user.setAgency(agency);
            userRepository.save(user);
        }
    }
}



