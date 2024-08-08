package org.example.tour_guide.service;

import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.Offer;

import java.util.List;

public interface AgencyService {
    void createAgency(String companyName, String voen);

    Agency findById(Integer id);

    List<Offer> getOffersForAgency(Agency agency);
}
