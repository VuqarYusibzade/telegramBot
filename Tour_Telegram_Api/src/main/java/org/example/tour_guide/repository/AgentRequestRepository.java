package org.example.tour_guide.repository;

import org.example.tour_guide.enums.RequestStatus;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import org.example.tour_guide.models.AgentRequest;


import java.util.List;

@Repository
public interface AgentRequestRepository extends JpaRepository<AgentRequest, Integer> {
    List<AgentRequest> findByStatus(RequestStatus status);
    List<AgentRequest> findByAgencyAndStatus(Agency agency, RequestStatus status);

    List<AgentRequest> findByAgency(Agency agency);

}

