package org.example.tour_guide.repository;

import org.example.tour_guide.models.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency,Integer> {

}
