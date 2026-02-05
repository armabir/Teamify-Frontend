package org.example.teamifyfrontend.controller;

import jakarta.servlet.http.HttpSession;
import org.example.teamifyfrontend.client.UserApiClient;
import org.example.teamifyfrontend.dto.UserDto;
import org.example.teamifyfrontend.enums.Gender;
import org.example.teamifyfrontend.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class AuthenticationController {

    private final UserApiClient userApiClient;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(UserApiClient userApiClient, PasswordEncoder passwordEncoder) {
        this.userApiClient = userApiClient;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String signup(Model model){

        model.addAttribute("userDto", new UserDto("", "", "", "", "", "", "","", null, ""));

        return "signup";
    }

    @PostMapping("/signup")
    public String getSignup(@ModelAttribute UserDto dto){

        if (userApiClient.getUserByUsername(dto.username()) != null){
            return "redirect:signup?username";
        }
        if (userApiClient.getUserByEmail(dto.email()) != null){
            return "redirect:signup?email";
        }

        User user = new User();
        user.setUserName(dto.username().toLowerCase()); // username converted to lower case
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password())); // encrypted password

        user.setProjectList(new ArrayList<>());
        user.setExperienceList(new ArrayList<>());
        user.setEducationList(new ArrayList<>());
        user.setInterests(new ArrayList<>());
        user.setSkills(new ArrayList<>());
        user.setLookingForList(new ArrayList<>());
        user.setSocialMediaLinkList(new ArrayList<>());

        userApiClient.createUser(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){

        model.addAttribute("userDto", new UserDto("", "", "", "", "", "", "","", null, ""));

        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute UserDto dto, HttpSession session){

        session.setAttribute("loggedUser", dto.username().toLowerCase());

        if (userApiClient.getUserByUsername(dto.username().toLowerCase()) == null){
            return "redirect:login?error";
        }
        if (passwordEncoder.matches(dto.password(), userApiClient.getUserByUsername(dto.username().toLowerCase()).getPassword())){ // encoded password matching
            return "redirect:login?error";
        }

        // send to add-info page
        if (userApiClient.getUserByUsername(dto.username()).getBio() == null){
            return "redirect:/add-info";
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/add-info")
    public String addInfo(Model model){

        model.addAttribute("userDto", new UserDto("", "", "", "", "", "", "","", null, ""));

        return "add-info";
    }

    @PostMapping("/add-info")
    public String addInfoPost(@ModelAttribute UserDto dto, HttpSession session){

        String loggedUsername = session.getAttribute("loggedUser").toString();
        User user = userApiClient.getUserByUsername(loggedUsername);

        user.setBio(dto.bio());
        user.setAbout(dto.about());
        user.setLocation(dto.location());
        user.setDateOfBirth(dto.dateOfBirth());
        user.setGender(Gender.valueOf(dto.gender()));

        userApiClient.updateUserBasicInfo(user.getId(), user);

        return "redirect:/profile";
    }
}
