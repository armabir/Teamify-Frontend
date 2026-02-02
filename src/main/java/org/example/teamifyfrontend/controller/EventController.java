package org.example.teamifyfrontend.controller;

import org.example.teamifyfrontend.client.OrganizationApiClient;
import org.example.teamifyfrontend.model.Event;
import org.example.teamifyfrontend.model.Organizer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

    private final OrganizationApiClient organizationApiClient;

    public EventController(OrganizationApiClient organizationApiClient) {
        this.organizationApiClient = organizationApiClient;
    }

    @GetMapping("/events")
    public String events(Model model){

//        List<Organizer> organizers = organizationApiClient.getAllOrganizers();
//        List<List<Event>> eventList = organizers.stream().map(organizer -> organizer.getEventList()).toList();
//        List<Event> eventss = new ArrayList<>();
//        for (List<Event> events : eventList){
//            eventss.addAll(events);
//        }

        List<Event> eventList = organizationApiClient.getOrganizerByName("SEU").getEventList();

        model.addAttribute("events", eventList);

        return "events";
    }
}
