package org.example.teamifyfrontend.client;

import org.example.teamifyfrontend.model.Event; // Ensure this model exists in frontend module
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EventApiClient {

    private final RestClient restClient;

    public EventApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // ---------- Core ----------

    public Event createEvent(Event event) {
        return restClient.post()
                .uri("/api/events")
                .body(event)
                .retrieve()
                .body(Event.class);
    }

    public Event getEventById(String id) {
        return restClient.get()
                .uri("/api/events/{id}", id)
                .retrieve()
                .body(Event.class);
    }

    public Event getEventByTitle(String title) {
        return restClient.get()
                .uri("/api/events/by-title/{title}", title)
                .retrieve()
                .body(Event.class);
    }

    public List<Event> getAllEvents() {
        return restClient.get()
                .uri("/api/events")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<Event> getEventsByType(String type) {
        return restClient.get()
                .uri("/api/events/by-type/{type}", type)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public Event updateEvent(String id, Event updatedEvent) {
        return restClient.patch()
                .uri("/api/events/{id}", id)
                .body(updatedEvent)
                .retrieve()
                .body(Event.class);
    }

    public void deleteEvent(String id) {
        restClient.delete()
                .uri("/api/events/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    // ---------- Team Management ----------

    public Event addTeamToEvent(String eventId, String teamName) {
        return restClient.post()
                .uri("/api/events/{eventId}/teams/{teamName}", eventId, teamName)
                .retrieve()
                .body(Event.class);
    }

    public Event removeTeamFromEvent(String eventId, String teamName) {
        return restClient.delete()
                .uri("/api/events/{eventId}/teams/{teamName}", eventId, teamName)
                .retrieve()
                .body(Event.class);
    }
}