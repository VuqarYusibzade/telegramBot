package org.example.tour_guide.dto;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private String sessionHistory;
    private Boolean isExpired;
    private LocalDateTime expireDate;
}
