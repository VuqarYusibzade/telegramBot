package org.example.tour_guide.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tour_guide.enums.Role;
import org.example.tour_guide.models.Agency;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;

    @Email
    private String email;

    private String password;

    private String confirmPassword;

    private Agency agency;

    private Set<Role> authorities;

}
