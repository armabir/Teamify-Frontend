package org.example.teamifyfrontend.dto;

import java.time.LocalDate;

public record EventDto(String title, String shortDescription, String fullDescription,
                       String eventType, String location) {
}