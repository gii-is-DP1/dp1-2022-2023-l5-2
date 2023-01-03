package org.springframework.samples.bossmonster.gameLobby;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "lobbies")
public class GameLobby extends BaseEntity { // Que extiende?//

    @ManyToOne(optional = false)
    @JoinColumn(name = "leader",referencedColumnName = "username")
    private User leaderUser;

    @NotNull
    private Integer maxPlayers;

    @ManyToMany
    @JoinTable(
        name = "lobby_users",
        joinColumns = @JoinColumn(name="lobby_id"),
        inverseJoinColumns = @JoinColumn(name= "user_id"))
    private List<User> joinedUsers;

    @OneToOne
    @JoinColumn(name = "game_id",nullable = true)
    Game game;

    public User addUser(User user) {
        if(getJoinedUsers() == null) setJoinedUsers(new ArrayList<>());
        if(getJoinedUsers().size() >= getMaxPlayers()) throw new IllegalArgumentException("Cannot add user, lobby is full");
        getJoinedUsers().add(user);
        return user;
    }

    public boolean removeUser(User user) {
        if(user == getLeaderUser()) throw new IllegalArgumentException("Cannot delete the leader user from their lobby");
        return getJoinedUsers().remove(user);
    }

    public boolean gameStarted() {return getGame() != null;}

    public boolean gameIsActive() {return !getGame().isActive();}

    public boolean isAcceptingNewPlayers() {
        return getJoinedUsers().size() < getMaxPlayers() && !gameStarted();
    }

}
