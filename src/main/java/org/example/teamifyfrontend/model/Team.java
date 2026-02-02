package org.example.teamifyfrontend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    private String id;

    private String name;
    private String createdBy;
    private String goal;
    private String description;
    private LocalDate createAt;
    private String coverImageUrl;

    private List<String> memberUsernames;
    private List<String> announcements;
}