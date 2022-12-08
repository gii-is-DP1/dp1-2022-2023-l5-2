package org.springframework.samples.bossmonster.user;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.statistics.Achievement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{

    @NotEmpty
	@Id
    @Size(min = 5, max = 20)
	private String username;

    @NotEmpty
    @Size(min = 3, max = 20)
    private String nickname;

    @NotEmpty
    @Size(min = 5, max = 40)
    @Email
    private String email;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String description;

    @Column(name = "avatar")
    private String avatar;

    @NotEmpty
    @Size(min = 6, max = 20)
	private String password;    

    private boolean enabled;
    
    @ManyToMany(mappedBy = "participants",cascade = CascadeType.ALL)
    private Set<GameResult> results;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

    @OneToMany(mappedBy = "winner")
    private List<GameResult> wins;

    @ManyToMany(mappedBy = "joinedUsers")
    private List<GameLobby> lobbies;

    @ManyToMany
    @JoinTable(
        name = "achievement_users",
        joinColumns = @JoinColumn(name="username"),
        inverseJoinColumns = @JoinColumn(name= "achievement_id"))
	private Set<Achievement> achievements;

    public Set<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(Set<Achievement> achievements) {
		this.achievements = achievements;
	}


    // @ManyToMany(mappedBy="users")
    // private Set<GameResult> results;

    // @OneToMany(mappedBy = "user")
    // private Set<Authorities> authorities;
}
