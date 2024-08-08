package org.example.tour_guide.repository;

import org.example.tour_guide.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Integer id);

    User findByEmail(String email);

    Optional<User> findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE is_active = true", nativeQuery = true)
    List<User> getActiveUsers();

}
