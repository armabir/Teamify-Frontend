package org.example.teamifyfrontend.dto;

import java.time.LocalDate;

public record UserDto(String firstName, String lastName, String username,
                      String email, String password, String bio, String about,
                      String location, LocalDate dateOfBirth, String gender) {
}
