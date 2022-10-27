package org.springframework.samples.petclinic.gameLobby;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GameLobby extends BaseEntity { // Que extiende?
    
    // Id?
    private String leaderUser;         // CAMBIAR TIPO A USER
    private String joinCode;
    private Integer maxPlayers;
    private List<String> joinedUsers;  // CAMBIAR TIPO A LIST<USER>

}