package org.example.tour_guide.service;

import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.AgentRequest;
import org.example.tour_guide.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface AgentRequestService {
    void createOfferForAgentRequest(Integer agentRequestId, String price, LocalDateTime startDate, LocalDateTime endDate, String spotName);
    void archiveAgentRequest(AgentRequest agentRequest);
    List<AgentRequest> getArchivedRequestsByAgency(Agency agency);
}
