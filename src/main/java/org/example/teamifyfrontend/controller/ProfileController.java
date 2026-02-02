package org.example.teamifyfrontend.controller;

import jakarta.servlet.http.HttpSession;
import org.example.teamifyfrontend.client.UserApiClient;
import org.example.teamifyfrontend.dto.EducationDto;
import org.example.teamifyfrontend.dto.ExperienceDto;
import org.example.teamifyfrontend.dto.ProjectDto;
import org.example.teamifyfrontend.model.Interest;
import org.example.teamifyfrontend.model.Skill;
import org.example.teamifyfrontend.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProfileController {

    private final UserApiClient userApiClient;

    public ProfileController(UserApiClient userApiClient) {
        this.userApiClient = userApiClient;
    }

    @GetMapping("/profile")
    private String profile2(Model model, HttpSession session,
                            @RequestParam(value = "view", required = false) String viewUsername){


        String usernameToFetch = session.getAttribute("loggedUser").toString();

        if (viewUsername != null && !viewUsername.isEmpty()) {
            usernameToFetch = viewUsername;
        }

        User user = userApiClient.getUserByUsername(usernameToFetch);

        if (user == null) {
            user = userApiClient.getUserByUsername(usernameToFetch);
        }

        // Add user to model so Thymeleaf renders THIS user's profile
        model.addAttribute("user", user);

        List<Skill> skills = userApiClient.getAllSkill();
        List<Interest> interests = userApiClient.getAllInterest();

        model.addAttribute("skills", skills);
        model.addAttribute("interests", interests);

        model.addAttribute("project", new ProjectDto("","","","",null,null,"","",""));
        model.addAttribute("experience", new ExperienceDto("","","","",null,null,""));
        model.addAttribute("education", new EducationDto("","","",null,null,""));

        return "profile";
    }

    @PostMapping("/profile")
    private String update(@ModelAttribute ProjectDto projectDto, @ModelAttribute ExperienceDto experienceDto,
                          @ModelAttribute EducationDto educationDto, HttpSession session){

        String loggedUser = session.getAttribute("loggedUser").toString();
        User user = userApiClient.getUserByUsername(loggedUser);

        if (projectDto.title() != null) {
            userApiClient.addProject(user.getId(), projectDto.toProject());
        }
        if (experienceDto.companyName() != null) {
            userApiClient.addExperience(user.getId(), experienceDto.toExperience());
        }
        if (educationDto.institutionName() != null){
            userApiClient.addEducation(user.getId(), educationDto.toEducation());
        }

        return "redirect:/profile";
    }

    @PostMapping("/upload-image")
    public String uploadImage(@RequestParam("image")MultipartFile multipartFile, HttpSession session) throws IOException {

        String loggedUser = session.getAttribute("loggedUser").toString();
        User user = userApiClient.getUserByUsername(loggedUser);

        if (multipartFile.isEmpty()) {
            return "redirect:/profile?error"; // Or add an error parameter
        }

        String uploadDir = "target/classes/static/images/uploads/";

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 3. Save File
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uniqueFileName = user.getId() + "_" + fileName;

        try (var inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        String fileUrl = "/images/uploads/" + uniqueFileName;
        User updatedUser = new User();
        updatedUser.setProfilePictureUrl(fileUrl);
        userApiClient.updateUserBasicInfo(user.getId(), updatedUser);

        return "redirect:/profile";
    }

    @PostMapping("/add-skill")
    public String updateSkills(@RequestParam(value = "skillIds", required = false) List<String> selectedSkillIds,
                               HttpSession session) {

        String loggedUser = session.getAttribute("loggedUser").toString();
        User user = userApiClient.getUserByUsername(loggedUser);
        String userId = user.getId();

        if (selectedSkillIds == null) {
            selectedSkillIds = new ArrayList<>();
        }

        List<String> currentSkillIds = user.getSkills().stream()
                .map(Skill::getId)
                .toList();

        for (String oldId : currentSkillIds) {
            if (!selectedSkillIds.contains(oldId)) {
                userApiClient.removeSkill(userId, oldId); // You need this method in your Client
            }
        }

        for (String newId : selectedSkillIds) {
            if (!currentSkillIds.contains(newId)) {
                userApiClient.addSkill(userId, newId);
            }
        }

        return "redirect:/profile";
    }

    @PostMapping("/update-interests")
    public String updateInterests(@RequestParam(value = "interestIds", required = false) List<String> selectedInterestIds,
                                  @RequestParam(value = "lookingFor", required = false) String lookingForRaw,
                                  HttpSession session) {

        String loggedUser = session.getAttribute("loggedUser").toString();
        User user = userApiClient.getUserByUsername(loggedUser);
        String userId = user.getId();

        // ==========================================
        // 1. SYNC INTERESTS (Add/Remove Logic)
        // ==========================================

        // Handle null case (if user unchecks everything)
        if (selectedInterestIds == null) {
            selectedInterestIds = new ArrayList<>();
        }

        // Get current DB interests
        List<String> currentInterestIds = user.getInterests().stream()
                .map(Interest::getId)
                .toList();

        // Remove unchecked interests
        for (String oldId : currentInterestIds) {
            if (!selectedInterestIds.contains(oldId)) {
                userApiClient.removeInterest(userId, oldId); // Ensure you have this method
            }
        }

        // Add newly checked interests
        for (String newId : selectedInterestIds) {
            if (!currentInterestIds.contains(newId)) {
                userApiClient.addInterest(userId, newId); // Ensure you have this method
            }
        }

        // ==========================================
        // 2. UPDATE "LOOKING FOR"
        // ==========================================

        List<String> lookingForList = new ArrayList<>();

        if (lookingForRaw != null && !lookingForRaw.isBlank()) {
            // Split by comma and trim whitespace from each item
            lookingForList = Arrays.stream(lookingForRaw.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }


        User updatedUser = new User();
        updatedUser.setLookingForList(lookingForList);
        userApiClient.updateUserBasicInfo(userId, updatedUser);

        return "redirect:/profile";
    }

    @GetMapping("/signout")
    public String signout(HttpSession session){

        session.invalidate();

        return "redirect:/landing";
    }

}
