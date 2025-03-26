package com.travelmate.service;

import com.travelmate.dto.UpdateTripRequest;
import com.travelmate.entity.Trip;
import com.travelmate.entity.User;
import com.travelmate.repository.TripRepository;
import com.travelmate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public Trip createTrip(String username, Trip trip) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        trip.setUser(user);
        return tripRepository.save(trip);
    }

    public List<Trip> getTripsByUsername(String username, String destination) {
        if (destination != null && !destination.isBlank()) {
            return tripRepository.findByUserUsernameAndDestinationContainingIgnoreCase(username, destination);
        } else {
            return tripRepository.findByUserUsername(username);
        }
    }

    public Trip updateTrip(Long tripId, UpdateTripRequest request, String username) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Viaggio non trovato"));

        if (!trip.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Non sei autorizzato a modificare questo viaggio");
        }

        if (request.getDestination() != null) trip.setDestination(request.getDestination());
        if (request.getStartDate() != null) trip.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) trip.setEndDate(request.getEndDate());
        if (request.getDescription() != null) trip.setDescription(request.getDescription());

        return tripRepository.save(trip);
    }

    public void deleteTrip(Long tripId, String username) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Viaggio non trovato"));

        if (!trip.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Non sei autorizzato a eliminare questo viaggio");
        }

        tripRepository.delete(trip);
    }
}
