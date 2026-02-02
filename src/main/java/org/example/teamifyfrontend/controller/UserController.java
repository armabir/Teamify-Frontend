package org.example.teamifyfrontend.controller;

import org.example.teamifyfrontend.client.UserApiClient;
import org.example.teamifyfrontend.enums.Gender;
import org.example.teamifyfrontend.model.Project;
import org.example.teamifyfrontend.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class UserController {

    private final UserApiClient userApiClient;

    public UserController(UserApiClient userApiClient) {
        this.userApiClient = userApiClient;
    }

    @PostMapping("/users/signup")
    @ResponseBody
    public String signup(){

        User user = new User();
        user.setUserName("talha");
        user.setBio("I am a developer");
        user.setGender(Gender.MALE);
        user.setFirstName("Abu");
        user.setLastName("Talha");

        user.setProjectList(new ArrayList<>());
        user.setExperienceList(new ArrayList<>());
        user.setEducationList(new ArrayList<>());
        user.setSocialMediaLinkList(new ArrayList<>());
        user.setSkills(new ArrayList<>());
        user.setInterests(new ArrayList<>());

        userApiClient.createUser(user);

        return "Successful";
    }

    @PostMapping("/user/project")
    @ResponseBody
    public String project(){

        Project project = new Project();
        project.setTitle("Opced");
        project.setRole("CEO");

        project.setTechStacks(new ArrayList<>());
        project.setImageUrls(new ArrayList<>());

        userApiClient.addProject("69776f8a4718fca52d1c7740", project);

        return "Added Project";
    }
}
