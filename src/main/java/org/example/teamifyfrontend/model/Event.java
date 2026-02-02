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
public class Event {

    private String id;

    private String title;

    private String shortDescription;
    private String fullDescription;
    private String eventType;
    private String location;
    private String coverUrl;
    private LocalDate createdAt;
//    private boolean isPaid;

    private List<String> teamNames;
}