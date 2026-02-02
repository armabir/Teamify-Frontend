package org.example.teamifyfrontend.dto;

import org.example.teamifyfrontend.model.Experience;
import org.example.teamifyfrontend.model.Project;

import java.time.LocalDate;
import java.util.List;

public record ExperienceDto(
        String companyName, String role, String location, String description,
        LocalDate startDate, LocalDate endDate, String companyImageUrl) {

    public Experience toExperience() {
        // This assumes your Experience class has a constructor matching these arguments
        return new Experience(companyName, role, location, description, startDate, endDate, companyImageUrl);
    }
}