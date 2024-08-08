package org.example.tour_guide.service;

import org.example.tour_guide.dto.ChangePasswordRequest;
import org.example.tour_guide.dto.UserDTO;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.Offer;
import org.example.tour_guide.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService  extends UserDetailsService {

   void createUserForAgent(Agency agency, String username, String email, String password);

   List<User> getActiveUsers();

   void registerUser(UserDTO userDTO);

   void verifyToken(String token);

   void changePassword(ChangePasswordRequest request, Principal connectUser);
   List<User> getAll();

   public void addAgencyToUser(String username, Agency agency);
   Optional<User> getByUserName(String username);
}
