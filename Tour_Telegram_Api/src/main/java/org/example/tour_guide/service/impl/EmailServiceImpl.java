package org.example.tour_guide.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.tour_guide.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static org.example.tour_guide.utils.EmailUtils.*;
import static org.example.tour_guide.utils.EmailUtils.forgotPasswordUrl;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sentMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Activate your Tour Guide App account");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(getEmailMessage(name,host,token));
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public void sendPasswordResetEmail(String email, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Set Password");
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(forgotPasswordUrl(host,resetToken));
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
