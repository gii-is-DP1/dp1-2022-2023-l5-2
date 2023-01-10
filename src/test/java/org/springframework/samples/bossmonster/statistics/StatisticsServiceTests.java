package org.springframework.samples.bossmonster.statistics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.invitations.InvitationService;
import org.springframework.samples.bossmonster.social.FriendRequestService;

import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class),
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {GameService.class,FriendRequestService.class,InvitationService.class}))
public class StatisticsServiceTests {

    @Autowired
    protected StatisticsService statisticsService;
    
    @Test
    @DisplayName("Find All Game Results by User")
    public void shouldFindAllGameResultUser(){
        List<GameResult> games=this.statisticsService.findAll("ignarrman");
        assertThat(games.size()).isEqualTo(4);

        games=this.statisticsService.findAll("admin1");
        assertThat(games.size()).isEqualTo(0);
    }
    
    @Test
    @DisplayName("Find User Victories")
    public void shouldFindUserVictories(){
        List<GameResult> victories= this.statisticsService.findAllWinned("ignarrman");
        assertThat(victories.size()).isEqualTo(2);

        victories= this.statisticsService.findAllWinned("eletomvel");
        assertThat(victories.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("calculate Win Rate")
    public void shouldCalculatedWinRate(){
        Double winRate= this.statisticsService.winRate("user1");
        assertThat(winRate).isEqualTo(50.0);

        winRate= this.statisticsService.winRate("igngongon2");
        assertThat(winRate).isEqualTo(0.);

        winRate=this.statisticsService.winRate("admin1");
        assertThat(winRate).isEqualTo(0.);     
    }

    @Test
    @DisplayName("calculate Average Duration")
    public void shouldCalculateAverageDuration(){
        Double duration= this.statisticsService.averageDuration("ignarrman");
        assertThat(duration).isEqualTo(36.0);

        duration=this.statisticsService.averageDuration("admin1");
        assertThat(duration).isEqualTo(0.);
    }

    @Test
    @DisplayName("calculate Best winStreak")
    public void shouldCalculateBestWinStreak(){
        List<GameResult> games=this.statisticsService.findAll("ignarrman");
        Integer winStreak= this.statisticsService.winStreakUser(games,"ignarrman");
        assertThat(winStreak).isEqualTo(2);

        List<GameResult> games2=this.statisticsService.findAll("admin1");
        winStreak=this.statisticsService.winStreakUser(games2, "admin1");
        assertThat(winStreak).isEqualTo(0);
    }

    @Test
    @DisplayName("create Ranking por winRate")
    public void testRankingPorWinRate(){

        List<Map.Entry<String,Double>> ranking = statisticsService.rankingPorWinRate();

        assertThat(ranking.get(0).getKey()).isNotEqualTo(ranking.get(1).getKey());
        assertTrue(ranking.get(0).getValue() >= ranking.get(1).getValue());

        assertThat(ranking.get(1).getKey()).isNotEqualTo(ranking.get(2).getKey());
        assertFalse(ranking.get(1).getValue() < ranking.get(2).getValue());    
    }

    @Test
    @DisplayName("create Ranking por Wins")
    public void testRankingPorWins(){

        List<Entry<String, Integer>> ranking = statisticsService.rankingPorWins();

        assertThat(ranking.get(0).getKey()).isNotEqualTo(ranking.get(1).getKey());
        assertTrue(ranking.get(0).getValue() >= ranking.get(1).getValue());

        assertThat(ranking.get(1).getKey()).isNotEqualTo(ranking.get(2).getKey());
        assertFalse(ranking.get(1).getValue() < ranking.get(2).getValue());
    }

}
