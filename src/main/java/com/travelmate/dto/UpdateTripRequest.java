package com.travelmate.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateTripRequest {
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
