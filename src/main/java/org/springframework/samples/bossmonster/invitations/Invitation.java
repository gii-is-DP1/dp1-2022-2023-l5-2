package org.springframework.samples.bossmonster.invitations;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invitations")
public class Invitation extends BaseEntity{
    
    @ManyToOne
    @JoinColumn(name = "lobby")
    @NotNull
    private GameLobby lobby;

    @ManyToOne
    @JoinColumn(name = "user")
    @NotNull
    private User user;

}
