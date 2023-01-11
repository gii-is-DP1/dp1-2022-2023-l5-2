package org.springframework.samples.bossmonster.user;

import java.util.List;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.statistics.Achievement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Audited
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
    @Size(min = 6, max = 120)
	private String password;    

    private boolean enabled;
    
    @ManyToMany(mappedBy = "participants",cascade = CascadeType.ALL)
    @NotAudited
    private Set<GameResult> results;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@NotAudited
    private Set<Authorities> authorities;

    @OneToMany(mappedBy = "winner")
    @NotAudited
    private List<GameResult> wins;

    @ManyToMany(mappedBy = "joinedUsers")
    @NotAudited
    private List<GameLobby> lobbies;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "achievement_users",
        joinColumns = @JoinColumn(name="username"),
        inverseJoinColumns = @JoinColumn(name= "achievement_id"))
    @NotAudited
    private Set<Achievement> achievements;

    public Set<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(Set<Achievement> achievements) {
		this.achievements = achievements;
	}


}
