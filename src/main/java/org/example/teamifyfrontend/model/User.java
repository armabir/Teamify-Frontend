package org.example.teamifyfrontend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.teamifyfrontend.enums.Gender;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String userName;
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String bio;
    private String about;
    private String location;
    private LocalDate dateOfBirth;
    private Gender gender;
    private LocalDate createdAt;

    private String profilePictureUrl;
    private String CoverPictureUrl;

    // Relationships / Lists
    private List<Project> projectList;
    private List<Experience> experienceList;
    private List<Education> educationList;

    // Basic only link for now. later add icons & shortner
    private List<String> socialMediaLinkList;
    private List<String> lookingForList;

    private List<Skill> skills;
    private List<Interest> interests;

}
