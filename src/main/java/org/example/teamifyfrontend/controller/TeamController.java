package org.example.teamifyfrontend.controller;

import org.example.teamifyfrontend.client.TeamApiClient;
import org.example.teamifyfrontend.client.UserApiClient;
import org.example.teamifyfrontend.dto.TeamDto;
import org.example.teamifyfrontend.model.Team;
import org.example.teamifyfrontend.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class TeamController {

    private final TeamApiClient teamApiClient;
    // Assuming UserApiClient is used elsewhere or later
    private final UserApiClient userApiClient;

    // Define where to save images (Project Local Path)
    // NOTE: In production, use an external path or AWS S3. 
    // For local dev, this saves to target/classes... and src/...
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public TeamController(TeamApiClient teamApiClient, UserApiClient userApiClient) {
        this.teamApiClient = teamApiClient;
        this.userApiClient = userApiClient;
    }

    @GetMapping("/teams")
    public String team(Model model, HttpSession session) {

        String loggedUsername = session.getAttribute("loggedUser").toString();

        List<Team> teamList = teamApiClient.getAllTeams();
        teamList.removeIf(team -> !team.getMemberUsernames().contains(loggedUsername));

        model.addAttribute("teams", teamList);
        model.addAttribute("user", loggedUsername);
        return "teams";
    }

    @PostMapping("/create-team")
    public String createTeam(@ModelAttribute TeamDto dto,
                             @RequestParam("coverImage") MultipartFile file,
                             HttpSession session) {

        String loggedUsername = session.getAttribute("loggedUser").toString();

        Team team = new Team();
        team.setName(dto.name());
        team.setGoal(dto.goal());
        team.setDescription(dto.description());
        team.setCreatedBy(loggedUsername);
        team.setMemberUsernames(List.of(loggedUsername));
        team.setCreateAt(LocalDate.now());

        // --- File Upload Logic ---
        if (!file.isEmpty()) {
            try {
                // 1. Create directory if not exists
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 2. Generate unique filename (uuid_filename.jpg)
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);

                // 3. Save file
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // 4. Set URL for DB (Access via /uploads/filename)
                team.setCoverImageUrl("/uploads/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle error (maybe add an error message to model)
            }
        } else {
            // Set a default gradient or placeholder if no image uploaded
            team.setCoverImageUrl(null);
        }

        teamApiClient.createTeam(team);

        return "redirect:/teams";
    }

    @GetMapping("/manage-team")
    public String manageTeam(@RequestParam(value = "teamName", required = false) String teamName,
                             @RequestParam(value = "view", required = false) String viewTeamName,
                             Model model,
                             HttpSession session) {

        // 1. Handle "view" parameter logic
        // If 'view' is present in URL, use it. Otherwise use 'teamName'.
        String targetName = (viewTeamName != null && !viewTeamName.isEmpty()) ? viewTeamName : teamName;

        if (targetName == null) return "redirect:/teams";

        Team team = teamApiClient.getTeamByName(targetName);
        if (team == null) return "redirect:/teams";

        // 2. Fetch Members (Existing logic)
        List<User> teamMembers = new ArrayList<>();
        for (String m : team.getMemberUsernames()) {
            teamMembers.add(userApiClient.getUserByUsername(m));
        }

        // 3. Determine if Logged-in User is a Member
        // (Assuming you stored "loggedUser" in session during login)
        String loggedUser = (String) session.getAttribute("loggedUser");
        boolean isMember = team.getMemberUsernames().contains(loggedUser);

        // 4. Pass Data to View
        model.addAttribute("team", team);
        model.addAttribute("teamMembers", teamMembers);
        model.addAttribute("announcements", team.getAnnouncements());
        model.addAttribute("isMember", isMember); // <--- Crucial Flag

        return "manage-team";
    }

    // --- NEW JOIN TEAM ENDPOINT ---
    @PostMapping("/join-team")
    public String joinTeam(@RequestParam("teamName") String teamName, HttpSession session) {
        String loggedUser = (String) session.getAttribute("loggedUser");

        // Call API to add the logged-in user to the team
        teamApiClient.addMember(teamName, loggedUser);

        // Redirect to the normal manage view (member view)
        return "redirect:/manage-team?teamName=" + URLEncoder.encode(teamName, StandardCharsets.UTF_8);
    }

    @PostMapping("/add-member")
    private String addMember(@RequestParam("teamName") String teamName, @RequestParam("username") String memberName){

        Team team = teamApiClient.getTeamByName(teamName);

        System.out.println(teamName);
        System.out.println(memberName);

        teamApiClient.addMember(team.getId(), memberName);

        return "redirect:/manage-team?teamName=" + URLEncoder.encode(teamName, StandardCharsets.UTF_8);
    }

    @PostMapping("/remove-member")
    public String removeMember(@RequestParam("teamName") String teamName,
                               @RequestParam("username") String username) {

        Team team = teamApiClient.getTeamByName(teamName);
        teamApiClient.removeMember(team.getId(), username);

        return "redirect:/manage-team?teamName=" + URLEncoder.encode(teamName, StandardCharsets.UTF_8);
    }

    @PostMapping("/add-announcement")
    public String addAnnouncement(@RequestParam("teamName") String teamName,
                                  @RequestParam("announcement") String announcement){

        Team team = teamApiClient.getTeamByName(teamName);
        teamApiClient.addAnnouncement(team.getId(), announcement);

        return "redirect:/manage-team?teamName=" + URLEncoder.encode(teamName, StandardCharsets.UTF_8);
    }
}