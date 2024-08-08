package org.example.tour_guide.controller;

import lombok.RequiredArgsConstructor;
import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.Offer;
import org.example.tour_guide.service.AgencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencies")
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;

    @PostMapping("/create")
    public ResponseEntity<Void> createAgency(@RequestBody Agency agency) {
        agencyService.createAgency(agency.getCompanyName(), agency.getVoen());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/offers")
    public ResponseEntity<List<Offer>> getOffersForAgencyUsers(@PathVariable Integer id) {
        Agency agency = new Agency();
        agency.setId(id);
        List<Offer> offers = agencyService.getOffersForAgency(agency);
        return ResponseEntity.ok(offers);
    }


}
