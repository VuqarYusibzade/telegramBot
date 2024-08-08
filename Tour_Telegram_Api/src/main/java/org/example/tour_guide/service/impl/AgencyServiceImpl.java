package org.example.tour_guide.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.tour_guide.enums.RequestStatus;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.AgentRequest;
import org.example.tour_guide.models.Offer;
import org.example.tour_guide.repository.AgencyRepository;
import org.example.tour_guide.repository.AgentRequestRepository;
import org.example.tour_guide.service.AgencyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {
    private final AgencyRepository agencyRepository;
    private final AgentRequestRepository agentRequestRepository;

    @Override
    public void createAgency(String companyName, String voen) {
        Agency agency = new Agency();
        agency.setCompanyName(companyName);
        agency.setVoen(voen);
        agencyRepository.save(agency);

        archiveOldRequests();
    }

    private void archiveOldRequests() {
        List<AgentRequest> oldRequests = agentRequestRepository.findByStatus(RequestStatus.PENDING);
        for (AgentRequest oldRequest : oldRequests) {
            oldRequest.setStatus(RequestStatus.ARCHIVED);
            agentRequestRepository.save(oldRequest);
        }
    }

    @Override
    public List<Offer> getOffersForAgency(Agency agency) {
        List<AgentRequest> agentRequests = agentRequestRepository.findByAgency(agency);
        List<Offer> offers = new ArrayList<>();
        for (AgentRequest agentRequest : agentRequests) {
            offers.add(agentRequest.getOffer());
        }
        return offers;
    }

    @Override
    public Agency findById(Integer id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agency not found with ID: " + id));
    }
}
