package org.example.teamifyfrontend.dto;

import org.example.teamifyfrontend.model.Education;
import org.example.teamifyfrontend.model.Experience;

import java.time.LocalDate;

public record EducationDto(
        String institutionName,
        String degree,
        String fieldOfStudy,
        LocalDate startDate,
        LocalDate endDate,
        String institutionImageUrl
) {
    public Education toEducation() {
        // Maps the DTO data directly to the Education constructor
        return new Education(
                institutionName,
                degree,
                fieldOfStudy,
                startDate,
                endDate,
                institutionImageUrl
        );
    }
}