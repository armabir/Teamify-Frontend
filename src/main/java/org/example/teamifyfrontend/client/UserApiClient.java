package org.example.teamifyfrontend.client;

import org.example.teamifyfrontend.model.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
public class UserApiClient {

    private final RestClient restClient;

    public UserApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // ---------- User ----------

    public User createUser(User user) {
        return restClient.post()
                .uri("/api/users")
                .body(user)
                .retrieve()
                .body(User.class);
    }

    public User getUserById(String userId) {
        return restClient.get()
                .uri("/api/users/{userId}", userId)
                .retrieve()
                .body(User.class);
    }

    public User getUserByUsername(String username) {
        return restClient.get()
                .uri("/api/users/by-username/{username}", username)
                .retrieve()
                .body(User.class);
    }

    public User getUserByEmail(String email) {
        return restClient.get()
                .uri("/api/users/by-email/{email}", email)
                .retrieve()
                .body(User.class);
    }

    public List<User> getAllUsers() {
        return restClient.get()
                .uri("/api/users")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public User updateUserBasicInfo(String userId, User updatedUser) {
        return restClient.patch()
                .uri("/api/users/{userId}", userId)
                .body(updatedUser)
                .retrieve()
                .body(User.class);
    }


    // ---------- Projects ----------

    public User addProject(String userId, Project project) {
        return restClient.post()
                .uri("/api/users/{userId}/projects", userId)
                .body(project)
                .retrieve()
                .body(User.class);
    }

    public User updateProject(String userId, String projectId, Project updatedProject) {
        return restClient.patch()
                .uri("/api/users/{userId}/projects/{projectId}", userId, projectId)
                .body(updatedProject)
                .retrieve()
                .body(User.class);
    }

    public User deleteProject(String userId, String projectId) {
        return restClient.delete()
                .uri("/api/users/{userId}/projects/{projectId}", userId, projectId)
                .retrieve()
                .body(User.class);
    }

    // ---------- Experience ----------

    public User addExperience(String userId, Experience experience) {
        return restClient.post()
                .uri("/api/users/{userId}/experiences", userId)
                .body(experience)
                .retrieve()
                .body(User.class);
    }

    public User updateExperience(String userId, String experienceId, Experience updatedExperience) {
        return restClient.patch()
                .uri("/api/users/{userId}/experiences/{experienceId}", userId, experienceId)
                .body(updatedExperience)
                .retrieve()
                .body(User.class);
    }

    public User deleteExperience(String userId, String experienceId) {
        return restClient.delete()
                .uri("/api/users/{userId}/experiences/{experienceId}", userId, experienceId)
                .retrieve()
                .body(User.class);
    }

    // ---------- Education ----------

    public User addEducation(String userId, Education education) {
        return restClient.post()
                .uri("/api/users/{userId}/educations", userId)
                .body(education)
                .retrieve()
                .body(User.class);
    }

    public User updateEducation(String userId, String educationId, Education updatedEducation) {
        return restClient.patch()
                .uri("/api/users/{userId}/educations/{educationId}", userId, educationId)
                .body(updatedEducation)
                .retrieve()
                .body(User.class);
    }

    public User deleteEducation(String userId, String educationId) {
        return restClient.delete()
                .uri("/api/users/{userId}/educations/{educationId}", userId, educationId)
                .retrieve()
                .body(User.class);
    }


    // ---------- Skills ----------

    public List<Skill> getAllSkill(){
        Skill[] skills = restClient.get()
                .uri("/api/skills")
                .retrieve()
                .body(Skill[].class);

        return Arrays.asList(skills);
    }

    public User addSkill(String userId, String skillId) {
        return restClient.post()
                .uri("/api/users/{userId}/skills/{skillId}", userId, skillId)
                .retrieve()
                .body(User.class);
    }

    public User removeSkill(String userId, String skillId) {
        return restClient.delete()
                .uri("/api/users/{userId}/skills/{skillId}", userId, skillId)
                .retrieve()
                .body(User.class);
    }

    // ---------- Interests ----------

    public List<Interest> getAllInterest(){
        Interest[] interests = restClient.get()
                .uri("/api/interests")
                .retrieve()
                .body(Interest[].class);

        return Arrays.asList(interests);
    }

    public User addInterest(String userId, String interestId) {
        return restClient.post()
                .uri("/api/users/{userId}/interests/{interestId}", userId, interestId)
                .retrieve()
                .body(User.class);
    }

    public User removeInterest(String userId, String interestId) {
        return restClient.delete()
                .uri("/api/users/{userId}/interests/{interestId}", userId, interestId)
                .retrieve()
                .body(User.class);
    }
}
