package track.user.trackuseractivity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import track.user.trackuseractivity.model.User;
import track.user.trackuseractivity.model.UserActivity;

import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity,Integer> {
    @Query("from UserActivity as u where u.user.id=:userId")
    List<UserActivity> findUserActivitiesByUser(@Param("userId") int userId);
}
