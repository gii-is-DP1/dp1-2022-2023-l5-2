package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AchievementRepository extends CrudRepository <Achievement,Integer> {
    List<Achievement> findAll();
}
