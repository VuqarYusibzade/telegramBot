package org.example.tour_guide.controller;

import lombok.RequiredArgsConstructor;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.AgentRequest;
import org.example.tour_guide.repository.AgentRequestRepository;
import org.example.tour_guide.service.AgencyService;
import org.example.tour_guide.service.AgentRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agent-requests")
@RequiredArgsConstructor
public class AgentRequestController {

    private final AgentRequestService agentRequestService;
    private final AgentRequestRepository agentRequestRepository;
    private final AgencyService agencyService;

    @PostMapping("/{agentRequestId}/offer")
    public ResponseEntity<Void> createOfferForAgentRequest(@PathVariable Integer agentRequestId, @RequestBody Map<String, String> request) {
        String price = request.get("price");
        String startDateStr = request.get("startDate");
        String endDateStr = request.get("endDate");
        String spotName = request.get("spotName");

        LocalDateTime startDate = LocalDateTime.parse(startDateStr);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr);

        agentRequestService.createOfferForAgentRequest(agentRequestId, price, startDate, endDate, spotName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/{agentRequestId}/archive")
    public ResponseEntity<Void> archiveAgentRequest(@PathVariable Integer agentRequestId) {
        AgentRequest agentRequest = agentRequestRepository.findById(agentRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Agent request not found with ID: " + agentRequestId));

        agentRequestService.archiveAgentRequest(agentRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/archived/{agencyId}")
    public ResponseEntity<List<AgentRequest>> getArchivedRequestsByAgency(@PathVariable Integer agencyId) {

        Agency agency = agencyService.findById(agencyId);
        if (agency == null) {
            return ResponseEntity.notFound().build();
        }

        List<AgentRequest> archivedRequests = agentRequestService.getArchivedRequestsByAgency(agency);
        return ResponseEntity.ok(archivedRequests);
    }

}
