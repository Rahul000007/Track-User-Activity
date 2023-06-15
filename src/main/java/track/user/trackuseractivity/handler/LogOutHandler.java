package track.user.trackuseractivity.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import track.user.trackuseractivity.model.User;
import track.user.trackuseractivity.model.UserActivity;
import track.user.trackuseractivity.repository.UserActivityRepository;
import track.user.trackuseractivity.repository.UserRepository;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class LogOutHandler implements LogoutSuccessHandler {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserActivityRepository userActivityRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String userName = authentication.getName();
        LocalDateTime currentDateTime =LocalDateTime.now();

        User user =this.userRepository.getUserByUsername(userName);
        List<UserActivity> userActivityList = this.userActivityRepository.findUserActivitiesByUser(user.getId());
        UserActivity lastUserActivityEntry= userActivityList.get(userActivityList.size()-1);
        String loginTime = lastUserActivityEntry.getLoginTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime lastLoginTime = LocalDateTime.parse(loginTime,formatter);
        Duration duration = Duration.between(lastLoginTime,currentDateTime);
        long seconds= duration.getSeconds();

        lastUserActivityEntry.setTimeSpend(seconds);
        this.userActivityRepository.save(lastUserActivityEntry);
        response.sendRedirect("/login");
    }
}
