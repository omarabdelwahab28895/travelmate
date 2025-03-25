package com.travelmate.controller;

import com.travelmate.entity.Trip;
import com.travelmate.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Trip savedTrip = tripService.createTrip(username, trip);
        return ResponseEntity.ok(savedTrip);
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getMyTrips(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<Trip> trips = tripService.getTripsByUsername(username);
        return ResponseEntity.ok(trips);
    }
}
