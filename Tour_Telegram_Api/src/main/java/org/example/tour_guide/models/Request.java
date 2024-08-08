package org.example.tour_guide.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String sessionHistory;
    private Boolean isExpired;
    private LocalDateTime expireDate;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<AgentRequest> agentRequest;
}
