package org.example.teamifyfrontend.client;

import org.example.teamifyfrontend.model.Event;
import org.example.teamifyfrontend.model.Organizer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class OrganizationApiClient {

    private final RestClient restClient;

    public OrganizationApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // ---------- Organization Core ----------

    public Organizer createOrganizer(Organizer organizer) {
        return restClient.post()
                .uri("/api/organizers")
                .body(organizer)
                .retrieve()
                .body(Organizer.class);
    }

    public Organizer getOrganizerById(String id) {
        return restClient.get()
                .uri("/api/organizers/{id}", id)
                .retrieve()
                .body(Organizer.class);
    }

    public Organizer getOrganizerByName(String name) {
        return restClient.get()
                .uri("/api/organizers/{name}", name)
                .retrieve()
                .body(Organizer.class);
    }

    public Organizer getOrganizerByEmail(String email) {
        // Using query parameter syntax for @RequestParam
        return restClient.get()
                .uri("/api/organizers/by-email?email={email}", email)
                .retrieve()
                .body(Organizer.class);
    }

    public List<Organizer> getAllOrganizers() {
        return restClient.get()
                .uri("/api/organizers")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public Organizer updateOrganizer(String id, Organizer updatedOrganizer) {
        return restClient.patch()
                .uri("/api/organizers/{id}", id)
                .body(updatedOrganizer)
                .retrieve()
                .body(Organizer.class);
    }

    public void deleteOrganizer(String id) {
        restClient.delete()
                .uri("/api/organizers/{id}", id)
                .retrieve()
                .toBodilessEntity(); // Since the controller returns void
    }

    // ---------- Event Management ----------

    public Organizer addEventToOrganizer(String organizerId, Event event) {
        return restClient.post()
                .uri("/api/organizers/{organizerId}/events/", organizerId)
                .body(event)
                .retrieve()
                .body(Organizer.class);
    }
}