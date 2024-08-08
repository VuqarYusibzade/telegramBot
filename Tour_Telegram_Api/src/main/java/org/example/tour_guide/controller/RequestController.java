package org.example.tour_guide.controller;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.example.tour_guide.dto.RequestDTO;
import org.example.tour_guide.models.Request;
import org.example.tour_guide.models.User;
import org.example.tour_guide.repository.RequestRepository;
import org.example.tour_guide.service.RequestService;
import org.example.tour_guide.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;
    private final RequestRepository requestRepository;

    @PostMapping("/create")
    public ResponseEntity<Void> createRequest(@RequestBody RequestDTO requestDTO) {
        Request newRequest = new Request();
        newRequest.setSessionHistory(requestDTO.getSessionHistory());
        newRequest.setIsExpired(requestDTO.getIsExpired());
        newRequest.setExpireDate(requestDTO.getExpireDate());

        List<User> activeUsers = userService.getActiveUsers();
//                .filter(Boolean::booleanValue)
//                .map(enabled -> new User())
//                .collect(Collectors.toList());

        requestService.createRequest(newRequest, activeUsers);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @PostMapping("/{requestId}/checkDeadline")
    public ResponseEntity<Void> checkDeadline(@PathVariable Integer requestId) {
        requestService.checkRequestsBeforeDeadline();
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
