package org.example.teamifyfrontend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Experience {

    private String experienceId = UUID.randomUUID().toString(); // unique identifier

    private String companyName;
    private String role;                   // role/title in the company
    private String location;               // city, country (optional)
    private String description;            // short summary of responsibilities
    private LocalDate startDate;
    private LocalDate endDate;             // can be null if currently working
    private String companyImageUrl;

    public Experience(String companyName, String role, String location, String description, LocalDate startDate, LocalDate endDate, String companyImageUrl) {
        this.companyName = companyName;
        this.role = role;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.companyImageUrl = companyImageUrl;
    }
}
