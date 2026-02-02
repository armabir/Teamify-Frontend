package org.example.teamifyfrontend.dto;

import java.time.LocalDate;
import java.util.List;

public record TeamDto(
        String name,
        String goal,
        String description) {}