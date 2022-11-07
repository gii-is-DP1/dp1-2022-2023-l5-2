package org.springframework.samples.petclinic.statistics;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Player;


public interface StatisticsRepository extends CrudRepository<Player,Integer> {
    List<Player> findAll();
    Player findById(int id);
;}
