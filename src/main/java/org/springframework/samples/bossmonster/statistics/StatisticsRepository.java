package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.bossmonster.game.player.Player;


public interface StatisticsRepository extends CrudRepository<Player,Integer> {
    List<Player> findAll();
    Player findById(int id);
;}
