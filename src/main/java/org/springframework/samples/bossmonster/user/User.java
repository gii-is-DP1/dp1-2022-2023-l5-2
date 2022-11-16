package org.springframework.samples.bossmonster.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.samples.bossmonster.gameResult.GameResult;

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

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private UserAvatar avatar;

    @NotEmpty
    @Size(min = 6, max = 20)
	private String password;

    private boolean enabled;
    
    @ManyToMany(mappedBy="participants",cascade = CascadeType.REMOVE)
    private Set<GameResult> results;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

    // @ManyToMany(mappedBy="users")
    // private Set<GameResult> results;

    // @OneToMany(mappedBy = "user")
    // private Set<Authorities> authorities;
}
