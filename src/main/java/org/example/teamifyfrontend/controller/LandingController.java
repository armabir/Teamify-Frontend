package org.example.teamifyfrontend.controller;

import jakarta.servlet.http.HttpSession;
import org.example.teamifyfrontend.client.TeamApiClient;
import org.example.teamifyfrontend.client.UserApiClient;
import org.example.teamifyfrontend.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

    private final UserApiClient userApiClient;
    private final TeamApiClient teamApiClient;

    public LandingController(UserApiClient userApiClient, TeamApiClient teamApiClient) {
        this.userApiClient = userApiClient;
        this.teamApiClient = teamApiClient;
    }

    @GetMapping("/landing")
    public String land(){
        return "/landing-v2";
    }

    @GetMapping("/landing-v2")
    public String land2(){
        return "/landing";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session){

        String loggedUser = session.getAttribute("loggedUser").toString();
        User currentUser = userApiClient.getUserByUsername(loggedUser);

        model.addAttribute("user", currentUser);
        model.addAttribute("teams", teamApiClient.getAllTeams());

        return "/dashboard";
    }
}
