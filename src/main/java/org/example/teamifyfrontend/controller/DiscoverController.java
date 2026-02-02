package org.example.teamifyfrontend.controller;

import jakarta.servlet.http.HttpSession;
import org.example.teamifyfrontend.client.TeamApiClient;
import org.example.teamifyfrontend.client.UserApiClient;
import org.example.teamifyfrontend.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DiscoverController {

    private final UserApiClient userApiClient;
    private final TeamApiClient teamApiClient;

    public DiscoverController(UserApiClient userApiClient, TeamApiClient teamApiClient) {
        this.userApiClient = userApiClient;
        this.teamApiClient = teamApiClient;
    }

    @GetMapping("/discover")
    public String discover(Model model, HttpSession session){

        String loggedUser = session.getAttribute("loggedUser").toString();
        User currentUser = userApiClient.getUserByUsername(loggedUser);

        List<User> userList = userApiClient.getAllUsers();
        userList.removeIf(u -> u.getId().equals(currentUser.getId()));

        model.addAttribute("users", userList);
        model.addAttribute("teams", teamApiClient.getAllTeams());

        return "discover-v2";
    }

    @GetMapping("/discover-v1")
    public String discover2(Model model, HttpSession session){

        String loggedUser = session.getAttribute("loggedUser").toString();
        User currentUser = userApiClient.getUserByUsername(loggedUser);

        List<User> userList = userApiClient.getAllUsers();
        userList.removeIf(u -> u.getId().equals(currentUser.getId()));

        model.addAttribute("users", userList);
        model.addAttribute("teams", teamApiClient.getAllTeams());

        return "discover-v3";
    }


}