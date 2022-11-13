package org.springframework.samples.bossmonster.gameResult;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GameResultRepository extends CrudRepository<GameResult,Integer> {

    GameResult findById(int id);

    @Query(value = "SELECT DISTINCT g.id,g.duration,g.winner,g.date FROM game_result g INNER JOIN results_users r ON r.user_id = ?1 AND r.game_result_id=g.id", nativeQuery = true)
    List<GameResult> findAllGameResultsUser(@Param(value = "username") String nameUser);

    @Query(value="SELECT r.user_id FROM results_users r WHERE r.game_result_id = ?1", nativeQuery = true)
    List<String> findAllParticipants(@Param(value = "gameResultId") int Id);

    @Query(value = "SELECT * FROM GAME_RESULT g WHERE g.winner =?1", nativeQuery = true)
    List<GameResult> findAllWinnedGamesUser(@Param(value = "username") String nameUser);
    
}
