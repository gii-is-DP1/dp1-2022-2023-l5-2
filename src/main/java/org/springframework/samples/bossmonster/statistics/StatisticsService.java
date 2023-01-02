package org.springframework.samples.bossmonster.statistics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.gameResult.GameResultRepository;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    
    private GameResultRepository repo;
    private UserRepository repoU;

    @Autowired
    public StatisticsService(GameResultRepository r,UserRepository repoU){
        this.repo=r;
        this.repoU=repoU;
    }

    List<GameResult> findAll(String Id){
        return repo.findAllGameResultsUser(Id);
    }
    List<GameResult> findAllWinned(String Id){
        return repo.findAllWinnedGamesUser(Id);
    }
    Double winRate(String Id){
        Double total=repo.findAllGameResultsUser(Id).size()*1.0;
        Double wins=repo.findAllWinnedGamesUser(Id).size()*1.0;
        Double result= 0.;
        if(total==0.0) result=total;
        else result=wins/total;
        return Math.floor(result*100*100)/100;
    }
    Double averageDuration(String Id){
        List<GameResult> games= repo.findAllGameResultsUser(Id);
        if(games.size()==0){
            return 0.;
        }else{
            Double duration= games.stream().mapToDouble(GameResult::getDuration).sum();
            return Math.floor((duration/games.size())*100)/100;
        }
    }
    Integer winStreakUser(List<GameResult> games, String username){
        Integer winStreak=0;
        Integer acumValue=0;
        for(Integer i=0; i<games.size();i++){
            if(games.get(i).getWinner().getUsername().equals(username)){
                acumValue++;
                if(acumValue>winStreak){
                    winStreak=acumValue;
                }
            }else{
                acumValue=0;
            } 
        }
        
        return winStreak;
    }
    Integer numPartidasGlobal(){
        List<GameResult> games= repo.findAll();
        return games.size();
    }
    Integer maxMinUsuarioPartidasGlobal(Boolean quieroElMaximo){
        List<User> users= repoU.findAll();
        if(quieroElMaximo==false){
            Integer min=1000;
            for(Integer i=0; i<users.size();i++){
                String username= users.get(i).getUsername();
                List<GameResult> played= findAll(username);
                if(played.size()<min) min=played.size();
            }
            return min;
        }else{
            Integer max=0;
            for(Integer i=0; i<users.size();i++){
                String username= users.get(i).getUsername();
                List<GameResult> played= findAll(username);
                if(played.size()>max) max=played.size();
            }
            return max;
        }
    }
    Double promedioNumPartidas(){
        List<GameResult> games= repo.findAll();
        List<User> users= repoU.findAll();
        return games.size()*1.0/users.size()*1.0;
    }
    Double promedioDuracionGlobal(){
        List<GameResult> games= repo.findAll();
        Double duracionTotal= games.stream().mapToDouble(GameResult::getDuration).sum();
        return duracionTotal/games.size();
    }
    Double maxMinDuracionGlobal(Boolean quieroElMaximo){
        List<GameResult> games= repo.findAll();
        if(quieroElMaximo==false){
            Double min=games.stream().mapToDouble(GameResult::getDuration).min().getAsDouble();
            return min;
        }else{
            Double max=games.stream().mapToDouble(GameResult::getDuration).max().getAsDouble();
            return max;
        } 
    }
    Double promedioJugadoresPartida(){
        //No tiene sentido comprobar el máximo o mínimo de jugadores por partida porque ya está definido(2-4)
        //El total de jugadores por partida tampoco se calculará globalmente porque no tiene mucho sentido (Por partida si aparece)
        List<GameResult> games= repo.findAll();
        Integer totalAcum= games.stream().map(GameResult::getParticipants).mapToInt(Set<User>::size).sum();
        Double result= (totalAcum/games.size())*1.0;
        return Math.floor(result);
    }
    List<Map.Entry<String,Double>> rankingPorWinRate(){
        List<User> users= repoU.findAll();
        Map<String,Double> mapa= users.stream().collect(Collectors.toMap(user->user.getUsername(), user-> winRate(user.getUsername())));
        List<Map.Entry<String,Double>> sortEntries=mapa.entrySet().stream().sorted(Comparator.comparingDouble(Map.Entry::getValue)).collect(Collectors.toList());
        List<Map.Entry<String,Double>> result=new ArrayList<>();
        for(Integer i=sortEntries.size()-1;i>=0;i--){
            result.add(sortEntries.get(i));
        }
        //Una vez que haya más de 10 usuarios se pone en el return result.sublist(0,11) para que solo salgan los 10 primeros
        return result;
    }
    List<Map.Entry<String,Integer>> rankingPorWins(){
        List<User> users= repoU.findAll();
        Map<String,Integer> mapa= users.stream().collect(Collectors.toMap(user->user.getUsername(), user-> findAllWinned(user.getUsername()).size()));
        List<Map.Entry<String,Integer>> sortEntries=mapa.entrySet().stream().sorted(Comparator.comparingDouble(Map.Entry::getValue)).collect(Collectors.toList());
        List<Map.Entry<String,Integer>> result=new ArrayList<>();
        for(Integer i=sortEntries.size()-1;i>=0;i--){
            result.add(sortEntries.get(i));
        }
        //Una vez que haya más de 10 usuarios se pone en el return result.sublist(0,11) para que solo salgan los 10 primeros
        return result;
    }
    

}
