package org.example.tour_guide;

import org.example.tour_guide.enums.RequestStatus;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.AgentRequest;
import org.example.tour_guide.models.Offer;
import org.example.tour_guide.repository.AgencyRepository;
import org.example.tour_guide.repository.AgentRequestRepository;
import org.example.tour_guide.service.impl.AgencyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AgencyServiceImplTest {

    @InjectMocks
    private AgencyServiceImpl agencyService;

    @Mock
    private AgencyRepository agencyRepository;

    @Mock
    private AgentRequestRepository agentRequestRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test createAgency method")
    void testCreateAgency() {
        String companyName = "Test Company";
        String voen = "1234567890";

        agencyService.createAgency(companyName, voen);

        verify(agencyRepository, times(1)).save(any(Agency.class));

    }

    @Test
    @DisplayName("Test getOffersForAgency method")
    void testGetOffersForAgency() {
        Agency agency = new Agency();
        agency.setId(1);
        Offer offer1 = new Offer();
        Offer offer2 = new Offer();
        List<AgentRequest> agentRequests = new ArrayList<>();
        agentRequests.add(new AgentRequest(1, agency, null, offer1, null, RequestStatus.PENDING));
        agentRequests.add(new AgentRequest(2, agency, null, offer2, null, RequestStatus.PENDING));

        when(agentRequestRepository.findByAgency(agency)).thenReturn(agentRequests);

        List<Offer> offers = agencyService.getOffersForAgency(agency);

        assertEquals(2, offers.size());
    }
}
