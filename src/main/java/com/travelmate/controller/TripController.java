package com.travelmate.controller;

import com.travelmate.dto.UpdateTripRequest;
import com.travelmate.entity.Trip;
import com.travelmate.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<Trip>> getMyTrips(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String destination
    ) {
        String username = userDetails.getUsername();
        List<Trip> trips = tripService.getTripsByUsername(username, destination);
        return ResponseEntity.ok(trips);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTripRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Trip updatedTrip = tripService.updateTrip(id, request, userDetails.getUsername());
        return ResponseEntity.ok(updatedTrip);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        tripService.deleteTrip(id, username);
        return ResponseEntity.noContent().build();
    }
}
