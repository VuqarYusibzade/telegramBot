package org.example.tour_guide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.User;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AgencyDTO {
    private String voen;
    private String companyName;
    private User user;



}
