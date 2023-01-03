package org.springframework.samples.bossmonster.statistics;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class),
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = GameService.class))
public class StatisticsServiceTests {

    @Autowired
    protected StatisticsService statisticsService;
    
    @Test
    public void shouldFindAllGameResultUser(){
        List<GameResult> games=this.statisticsService.findAll("ignarrman");
        assertThat(games.size()).isEqualTo(4);

        games=this.statisticsService.findAll("admin1");
        assertThat(games.size()).isEqualTo(0);

    }
    
    @Test
    public void shouldFindUserVictories(){
        List<GameResult> victories= this.statisticsService.findAllWinned("ignarrman");
        assertThat(victories.size()).isEqualTo(2);

        victories= this.statisticsService.findAllWinned("eletomvel");
        assertThat(victories.isEmpty()).isTrue();
    }

    @Test
    public void shouldCalculatedWinRate(){
        Double winRate= this.statisticsService.winRate("user1");
        assertThat(winRate).isEqualTo(50.0);

        winRate= this.statisticsService.winRate("igngongon2");
        assertThat(winRate).isEqualTo(0.);

        winRate=this.statisticsService.winRate("admin1");
        assertThat(winRate).isEqualTo(0.);     
    }

    @Test
    public void shouldCalculateAverageDuration(){
        Double duration= this.statisticsService.averageDuration("ignarrman");
        assertThat(duration).isEqualTo(2.58);

        duration=this.statisticsService.averageDuration("admin1");
        assertThat(duration).isEqualTo(0.);
    }

    @Test
    public void shouldCalculateBestWinStreak(){
        List<GameResult> games=this.statisticsService.findAll("ignarrman");
        Integer winStreak= this.statisticsService.winStreakUser(games,"ignarrman");
        assertThat(winStreak).isEqualTo(2);

        List<GameResult> games2=this.statisticsService.findAll("admin1");
        winStreak=this.statisticsService.winStreakUser(games2, "admin1");
        assertThat(winStreak).isEqualTo(0);
    }



}
