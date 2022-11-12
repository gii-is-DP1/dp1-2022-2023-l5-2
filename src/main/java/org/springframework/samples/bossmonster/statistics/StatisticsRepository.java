package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.user.User;

public interface StatisticsRepository extends CrudRepository<User,Integer>{
    @Query(value = "SELECT result FROM game_results WHERE result.participants = ?1 ")
    List<GameResult> findAllGameResultsUser(@Param(value = "username") String nameuser);

    @Query(value = "SELECT result FROM game_results WHERE result.winner =?1")
    List<GameResult> findAllWinnedGamesUser(@Param(value = "username") String nameUser);
}
