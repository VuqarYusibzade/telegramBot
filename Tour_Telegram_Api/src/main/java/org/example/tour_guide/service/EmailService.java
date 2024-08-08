package org.example.tour_guide.service;

public interface EmailService {

    void sentMailMessage(String name, String to, String token);
    void sendPasswordResetEmail(String email, String resetToken);
}
