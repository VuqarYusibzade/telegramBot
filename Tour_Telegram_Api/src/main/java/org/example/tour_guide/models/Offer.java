package org.example.tour_guide.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.tour_guide.enums.RequestStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String price;
    LocalDateTime startDate;
    LocalDateTime endDate;
    private String spotName;
    @JsonIgnore
    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL)
    private AgentRequest agentRequest;
}
