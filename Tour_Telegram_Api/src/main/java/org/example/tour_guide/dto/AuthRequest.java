package org.example.tour_guide.dto;

public record AuthRequest(
        String username,
        String password
){
}