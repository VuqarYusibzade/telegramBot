package org.example.tour_guide.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tour_guide.config.WorkHoursConfig;
import org.example.tour_guide.enums.RequestStatus;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.AgentRequest;
import org.example.tour_guide.models.Offer;
import org.example.tour_guide.repository.AgentRequestRepository;
import org.example.tour_guide.repository.OfferRepository;
import org.example.tour_guide.service.AgentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentRequestServiceImpl implements AgentRequestService {

    private final OfferRepository offerRepository;
    private final AgentRequestRepository agentRequestRepository;
    private final WorkHoursConfig workHoursConfig;
    @Transactional
    @Override
    public void createOfferForAgentRequest(Integer agentRequestId, String price, LocalDateTime startDate, LocalDateTime endDate, String spotName) {
        LocalDateTime currentTime = LocalDateTime.now();

        AgentRequest agentRequest = agentRequestRepository.findById(agentRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Agent request not found with ID: " + agentRequestId));

        if (workHoursConfig.isWorkingHours(currentTime.toLocalTime()) && workHoursConfig.isBeforeDeadline(LocalDateTime.now().toLocalTime())) {
            if (!agentRequest.getRequest().getIsExpired()) {
                if (agentRequest.getOffer() == null) {
                    Offer newOffer = new Offer();
                    newOffer.setPrice(price);
                    newOffer.setStartDate(startDate);
                    newOffer.setEndDate(endDate);
                    newOffer.setSpotName(spotName);
                    newOffer.setAgentRequest(agentRequest);

                    offerRepository.save(newOffer);

                    agentRequest.setOffer(newOffer);
                } else {
                    throw new IllegalStateException("The agent has already created an offer.");
                }
            } else {
                throw new IllegalStateException("Cannot create offer for an expired request.");
            }
        } else {
            throw new IllegalStateException("Cannot create offer outside of working hours or after the deadline.");
        }
    }

    @Override
    public void archiveAgentRequest(AgentRequest agentRequest) {
        if (agentRequest.getStatus() != null && !agentRequest.getStatus().equals(RequestStatus.ARCHIVED)) {
            agentRequest.setStatus(RequestStatus.ARCHIVED);
            agentRequestRepository.save(agentRequest);
        }
    }



    @Override
    public List<AgentRequest> getArchivedRequestsByAgency(Agency agency) {
        return agentRequestRepository.findByAgencyAndStatus(agency, RequestStatus.ARCHIVED);
    }
}
