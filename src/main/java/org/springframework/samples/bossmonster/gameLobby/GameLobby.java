package org.springframework.samples.bossmonster.gameLobby;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "lobbies")
public class GameLobby extends BaseEntity { // Que extiende?

    @ManyToOne
    @JoinColumn(name = "leader_user_username")
    private User leaderUser;
    @NotNull
    private Integer maxPlayers;
    @ManyToMany
    private List<User> joinedUsers;
    @OneToOne
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
