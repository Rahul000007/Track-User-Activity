package track.user.trackuseractivity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import track.user.trackuseractivity.model.User;
import track.user.trackuseractivity.model.UserActivity;
import track.user.trackuseractivity.repository.UserActivityRepository;
import track.user.trackuseractivity.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserActivityRepository userActivityRepo;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/do_register")
    public String doregister(@ModelAttribute("user") User user) {
        User user1 = this.userRepository.getUserByUsername(user.getUsername());
        if (user1 == null) {
            user.setRole("USER");
            this.userRepository.save(user);
            return "login";
        } else {
            return "register";
        }
    }


    @GetMapping("/done")
    public String loggedIn(Model model, Principal principal) {
        User user = this.userRepository.getUserByUsername(principal.getName());
        List<UserActivity> userActivity = this.userActivityRepo.findUserActivitiesByUser(user.getId());
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userActivity", userActivity);
        return "index";
    }


    @PostMapping("/store")
    public ResponseEntity<String> name(@RequestBody String data, Principal principal, HttpSession session) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        UserActivity userActivity = objectMapper.readValue(data, UserActivity.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime time = (LocalDateTime) session.getAttribute("login");
        String formattedTime = time.format(formatter);

        userActivity.setUserName(principal.getName());
        userActivity.setLoginTime(formattedTime);

        User user = this.userRepository.getUserByUsername(principal.getName());
        userActivity.setUser(user);
        user.getUserActivity().add(userActivity);
        this.userRepository.save(user);

        return ResponseEntity.ok("Data Stored");
    }
}
