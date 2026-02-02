package org.example.teamifyfrontend.controller;

import jakarta.servlet.http.HttpSession;
import org.example.teamifyfrontend.client.EventApiClient;
import org.example.teamifyfrontend.client.OrganizationApiClient;
import org.example.teamifyfrontend.dto.EventDto;
import org.example.teamifyfrontend.dto.OrganizerDto;
import org.example.teamifyfrontend.model.Event;
import org.example.teamifyfrontend.model.Organizer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class OrganizationController {

    private final OrganizationApiClient organizationApiClient;
    private final EventApiClient eventApiClient;
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/logos/";

    public OrganizationController(OrganizationApiClient organizationApiClient, EventApiClient eventApiClient) {
        this.organizationApiClient = organizationApiClient;
        this.eventApiClient = eventApiClient;
    }

    @GetMapping("/organizer")
    public String showOrganizerProfile(Model model, HttpSession session) {

        String loggedOrg = session.getAttribute("organizerName").toString();
        Organizer organizer = organizationApiClient.getOrganizerByName(loggedOrg);

//        List<Event> eventList = new ArrayList<>();
//        if (organizer.getEventList() != null){
//            for (Event e: organizer.getEventList()){
//                eventList.add(eventApiClient.getEventByTitle(e));
//            }
//        }

        model.addAttribute("organizer", organizer);
        model.addAttribute("events", organizer.getEventList());

        return "organizer";
    }

    @GetMapping("/organizer/signup")
    public String signup(Model model) {
        // Initialize with nulls/empty strings
        model.addAttribute("organizerDto", new OrganizerDto("", "", "", "", "", "", null));
        return "signup-organizer";
    }

    @PostMapping("/organizer/signup")
    public String getSignup(@ModelAttribute OrganizerDto dto) {

        // 1. Check for Duplicates (API calls)
        // Note: You might need to add a 'getOrganizerByName' method to your client similar to email
        if (organizationApiClient.getOrganizerByEmail(dto.email()) != null) {
            return "redirect:/organizer/signup?email";
        }

        // 2. Handle Image Upload
        String logoUrl = "/img/default-logo.png"; // Default fallback
        if (dto.logoFile() != null && !dto.logoFile().isEmpty()) {
            try {
                logoUrl = saveFile(dto.logoFile());
            } catch (IOException e) {
                e.printStackTrace(); // Keep it simple, just print error
            }
        }

        // 3. Map DTO to Entity
        Organizer organizer = new Organizer();
        organizer.setName(dto.name());
        organizer.setType(dto.type());
        organizer.setWebsiteUrl(dto.websiteUrl());
        organizer.setEmail(dto.email());
        organizer.setPassword(dto.password()); // Encryption happens in backend or here
        organizer.setDescription(dto.description());
        organizer.setLogoUrl(logoUrl);

        organizer.setCreatedAt(LocalDate.now());
        organizer.setEventList(new ArrayList<>()); // Initialize empty list

        // 4. Send to Backend
        organizationApiClient.createOrganizer(organizer);

        return "redirect:/organizer/login";
    }

    // Simple helper method for file saving
    private String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        return "/uploads/logos/" + filename;
    }

    @GetMapping("/organizer/login")
    public String showOrganizerLoginForm() {
        return "login-organizer"; // This looks for login-organizer.html in templates/
    }

    @PostMapping("/organizer/login")
    public String loginPost(@RequestParam(name = "username") String orgName,
                            @RequestParam(name = "password") String password, HttpSession session) {

        Organizer org = organizationApiClient.getOrganizerByName(orgName);
        if (org == null) {
            return "redirect:/organizer/login?error";
        }

        if (!org.getPassword().equals(password)) {
            return "redirect:/organizer/login?error";
        }

        session.setAttribute("loggedOrganizer", org.getId());
        session.setAttribute("organizerName", org.getName()); // Optional: for navbar display

        return "redirect:/organizer";
    }

    @PostMapping("/events/create")
    public String processCreateEvent(@ModelAttribute EventDto dto, HttpSession session) {

        String orgName = session.getAttribute("organizerName").toString();
        Organizer org = organizationApiClient.getOrganizerByName(orgName);

        System.out.println(dto);
        Event event = new Event();
        event.setEventType(dto.eventType());
        event.setLocation(dto.location());
        event.setTitle(dto.title());
        event.setShortDescription(dto.shortDescription());
        event.setFullDescription(dto.fullDescription());
        event.setTeamNames(List.of("Team_Go"));

        organizationApiClient.addEventToOrganizer(org.getId(), event);

        return "redirect:/organizer";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate();

        return "redirect:/landing";
    }
}