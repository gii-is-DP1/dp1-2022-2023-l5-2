package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AchievementRepository extends CrudRepository <Achievement, Integer> {
    List<Achievement> findAll();

    public Achievement findByName(String name);

    @Modifying
    @Query(value="DELETE FROM achievement_users WHERE achievement_id=?1", nativeQuery = true)
    void deleteAchievementFromUsers(@Param(value = "id") int id);
}
