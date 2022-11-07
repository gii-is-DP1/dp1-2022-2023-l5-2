package org.springframework.samples.bossmonster.gameLobby;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

}
