package org.example.teamifyfrontend.dto;

import org.example.teamifyfrontend.model.Project;

import java.time.LocalDate;
import java.util.List;

public record ProjectDto( String title, String description, String projectUrl,
                          String role, LocalDate startDate, LocalDate endDate, String coverImageUrl,
                          String imageUrls, String techStacks) {
    public Project toProject(){

        List<String> imageList = List.of(imageUrls.split(","));
        List<String> techStackList = List.of(techStacks.split(","));

        return new Project(title, description, projectUrl, role, startDate, endDate, coverImageUrl, imageList, techStackList);
    }
}
