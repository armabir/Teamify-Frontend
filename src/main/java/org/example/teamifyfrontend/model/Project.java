package org.example.teamifyfrontend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private String projectId = UUID.randomUUID().toString();  // unique identifier

    private String title;                 // unique per user (enforced in service)
    private String description;
    private String projectUrl;            // optional: GitHub, demo link
    private String role;                  // role of the user
    private LocalDate startDate;
    private LocalDate endDate;            // can be null if ongoing

    private String coverImageUrl;
    private List<String> imageUrls;       // multiple project pictures
    private List<String> techStacks;

    public Project(String title, String description, String projectUrl, String role, LocalDate startDate,
                   LocalDate endDate, String coverImageUrl, List<String> imageUrls, List<String> techStacks) {
        this.title = title;
        this.description = description;
        this.projectUrl = projectUrl;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImageUrl = coverImageUrl;
        this.imageUrls = imageUrls;
        this.techStacks = techStacks;
    }
}
