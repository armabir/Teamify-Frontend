package org.example.teamifyfrontend.client;

import org.example.teamifyfrontend.model.Team;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class TeamApiClient {

    private final RestClient restClient;

    public TeamApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // ---------- Team Core ----------

    public Team createTeam(Team team) {
        return restClient.post()
                .uri("/api/teams")
                .body(team)
                .retrieve()
                .body(Team.class);
    }

    public Team getTeamById(String teamId) {
        return restClient.get()
                .uri("/api/teams/{teamId}", teamId)
                .retrieve()
                .body(Team.class);
    }

    public Team getTeamByName(String name) {
        return restClient.get()
                .uri("/api/teams/by-name/{name}", name)
                .retrieve()
                .body(Team.class);
    }

    public List<Team> getTeamsByMember(String username) {
        return restClient.get()
                .uri("/api/teams/by-member/{username}", username)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<Team> getAllTeams() {
        return restClient.get()
                .uri("/api/teams")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public Team updateTeam(String teamId, Team updatedTeam) {
        return restClient.patch()
                .uri("/api/teams/{teamId}", teamId)
                .body(updatedTeam)
                .retrieve()
                .body(Team.class);
    }

    // ---------- Team Membership ----------

    public Team addMember(String teamId, String username) {
        return restClient.post()
                .uri("/api/teams/{teamId}/members/{username}", teamId, username)
                .retrieve()
                .body(Team.class);
    }

    public Team removeMember(String teamId, String username) {
        return restClient.delete()
                .uri("/api/teams/{teamId}/members/{username}", teamId, username)
                .retrieve()
                .body(Team.class);
    }

    // ---------- Announcements ----------

    public Team addAnnouncement(String teamId, String announcement) {
        return restClient.post()
                .uri("/api/teams/{teamId}/announcements", teamId)
                .body(announcement)
                .retrieve()
                .body(Team.class);
    }
}