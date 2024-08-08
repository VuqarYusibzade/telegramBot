package org.example.tour_guide.repository;

import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.Offer;
import org.example.tour_guide.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

}
