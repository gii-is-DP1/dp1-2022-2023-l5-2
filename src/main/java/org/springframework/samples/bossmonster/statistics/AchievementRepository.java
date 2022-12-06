package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AchievementRepository extends CrudRepository <Achievement, Integer> {
    List<Achievement> findAll();

    @Query("SELECT u.achievements FROM User u WHERE u.username=:username")
    public List<Achievement> findPlayerAchievements(@Param("username") String username);

    public Achievement findByName(String name);
}
