package org.springframework.samples.petclinic.gameLobby;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

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
