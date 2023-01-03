package org.springframework.samples.bossmonster.gameResult;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query(value = "UPDATE game_result SET winner=null WHERE winner=?1",nativeQuery = true)
    void setWinnerNull(@Param(value = "username") String winner);
    
    @Modifying
    @Query(value="DELETE FROM results_users WHERE user_id=?1", nativeQuery = true)
    void deleteParticipated(@Param(value = "username") String username);
    @Query(value = "SELECT * FROM GAME_RESULT g", nativeQuery = true)
    List<GameResult> findAll();
}
