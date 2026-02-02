package org.example.teamifyfrontend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Interest {

    private String id;      // MongoDB _id
    private String name;    // e.g., AI, Web Development, Startups
}
