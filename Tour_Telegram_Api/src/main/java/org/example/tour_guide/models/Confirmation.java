package org.example.tour_guide.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
import java.time.LocalDateTime;


@Entity
@Table(name="confirmations")
@Data
public class Confirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private Integer tokenId;

    @Column(name="token")
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Confirmation() {}

    public Confirmation(User user) {
        this.user = user;
        createdDate = LocalDateTime.now();
        token = UUID.randomUUID().toString();
    }

}