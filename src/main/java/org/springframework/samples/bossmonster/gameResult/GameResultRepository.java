package org.springframework.samples.bossmonster.gameResult;

import org.springframework.data.repository.CrudRepository;

public interface GameResultRepository extends CrudRepository<GameResult,Integer> {
    
}
