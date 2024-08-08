package org.example.tour_guide.service;

import org.example.tour_guide.models.Request;
import org.example.tour_guide.models.User;

import java.util.List;

public interface RequestService {
    void createRequest(Request request, List<User> isEnable);
    void checkRequestsBeforeDeadline();
}
