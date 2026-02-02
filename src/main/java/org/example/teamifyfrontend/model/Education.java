package org.example.teamifyfrontend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Education {

    private String educationId = java.util.UUID.randomUUID().toString(); // unique identifier

    private String institutionName;
    private String degree;                 // e.g., BSc, MSc, Diploma
    private String fieldOfStudy;           // e.g., Computer Science
    private LocalDate startDate;
    private LocalDate endDate;             // can be null if currently studying

    public Education(String institutionName, String degree, String fieldOfStudy, LocalDate startDate, LocalDate endDate, String institutionImageUrl) {
        this.institutionName = institutionName;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.institutionImageUrl = institutionImageUrl;
    }

    private String institutionImageUrl;    // optional logo/image
}
