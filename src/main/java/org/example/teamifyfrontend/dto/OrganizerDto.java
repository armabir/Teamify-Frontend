package org.example.teamifyfrontend.dto;

import org.springframework.web.multipart.MultipartFile;

public record OrganizerDto(String name, String type, String websiteUrl,
                           String email, String password, String description,
                           MultipartFile logoFile) {
}