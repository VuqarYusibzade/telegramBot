package org.example.tour_guide.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.tour_guide.config.WorkHoursConfig;
import org.example.tour_guide.enums.RequestStatus;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.AgentRequest;
import org.example.tour_guide.models.Request;
import org.example.tour_guide.models.User;
import org.example.tour_guide.repository.AgentRequestRepository;
import org.example.tour_guide.repository.RequestRepository;
import org.example.tour_guide.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    @Autowired
    private final AgentRequestRepository agentRequestRepository;

    @Autowired
    private final RequestRepository requestRepository;

    private final WorkHoursConfig workHoursConfig;
    @Override
    public void createRequest(Request request, List<User> users) {
        boolean hasActiveUserWithAgency = users.stream()
                .filter(User::isActive)
                .anyMatch(user -> user.getAgency() != null);

        if (!hasActiveUserWithAgency) {
            throw new IllegalStateException("No active user affiliated with any agency was found");
        }


        if (request.getId() == null) {

            requestRepository.save(request);
        }

        for (User user : users) {
            if (user.isActive() && user.getAgency() != null) {
                Agency agency = user.getAgency();
                AgentRequest agentRequest = new AgentRequest();
                agentRequest.setRequest(request);
                agentRequest.setAgency(agency);
                agentRequest.setStatus(RequestStatus.PENDING);
                agentRequestRepository.save(agentRequest);
            }
        }
    }






    @Scheduled(fixedRate = 60000)
    public void checkRequestsBeforeDeadline() {
        List<Request> requests = requestRepository.findAll();
        LocalTime currentTime = LocalTime.now();

        for (Request request : requests) {
            if (!request.getIsExpired()) {
                if (request.getExpireDate() != null) {
                    LocalTime expireTime = request.getExpireDate().toLocalTime();
                    if (currentTime.isAfter(expireTime) && workHoursConfig.isBeforeDeadline(currentTime)) {
                        request.setIsExpired(true);
                        requestRepository.save(request);
                    }
                }
            }
        }
    }
}

