package com.travelmate.service;

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

        trip.setUser(user); // Colleghiamo il viaggio all'utente
        return tripRepository.save(trip);
    }

    public List<Trip> getTripsByUsername(String username) {
        return tripRepository.findByUserUsername(username);
    }
}
