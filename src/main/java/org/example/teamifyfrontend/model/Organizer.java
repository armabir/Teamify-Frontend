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
public class Organizer {

    private String id;

    private String name;
    private String email;
    private String password;

    private String type;
    private String description;
    private String logoUrl;
    private String websiteUrl;
    private LocalDate createdAt;

    private List<Event> eventList;
}
