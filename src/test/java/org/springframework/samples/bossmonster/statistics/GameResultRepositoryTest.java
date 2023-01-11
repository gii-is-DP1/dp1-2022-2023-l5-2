package org.springframework.samples.bossmonster.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.gameResult.GameResultRepository;
import org.springframework.stereotype.Repository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class GameResultRepositoryTest {

    @Autowired
    private GameResultRepository repository;

    @Test
    @DisplayName("Delete the game result by id")
    void shouldFindGameResultById() {
        GameResult result = repository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("Find all the game results of the user")
    void shouldFindAllGameResultsUser() {
        List<GameResult> results = repository.findAllGameResultsUser("user1");
        assertEquals(2, results.size());
    }

    @Test
    @DisplayName("Find all the game results winned by the user")
    void shouldFindAllWinnedGamesUser() {
        List<GameResult> results = repository.findAllWinnedGamesUser("user1");
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Set to null a gameResult winned by the user")
    void shouldSetWinnerNull() {
        repository.setWinnerNull("user1");
        List<GameResult> results = repository.findAllWinnedGamesUser("user1");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Auxiliar deletion of results_users")
    void shouldDeleteGameResultParticipated() {
        repository.deleteParticipated("user1");
        List<GameResult> results = repository.findAllGameResultsUser("user1");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Find all game Results")
    void shouldFindAll() {
        List<GameResult> results = repository.findAll();
        assertEquals(6, results.size());
    }
}

