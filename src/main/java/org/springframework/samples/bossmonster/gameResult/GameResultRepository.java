package org.springframework.samples.bossmonster.gameResult;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GameResultRepository extends CrudRepository<GameResult,Integer> {

    GameResult findById(int id);

    @Query(value = "SELECT g FROM GameResult g JOIN g.participants r WHERE r.username = ?1")
    List<GameResult> findAllGameResultsUser(@Param(value = "username") String nameUser);

    @Query(value = "SELECT g FROM GameResult g WHERE g.winner.username =?1")
    List<GameResult> findAllWinnedGamesUser(@Param(value = "username") String nameUser);

    @Modifying
    @Query(value = "UPDATE GameResult SET winner=null WHERE winner.username=?1")
    void setWinnerNull(@Param(value = "username") String winner);
    
    @Modifying
    @Query(value="DELETE FROM results_users WHERE user_id=?1", nativeQuery = true)
    void deleteParticipated(@Param(value = "username") String username);
    @Query(value = "SELECT g FROM GameResult g")
    List<GameResult> findAll();
}
