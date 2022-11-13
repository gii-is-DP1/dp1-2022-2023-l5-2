package org.springframework.samples.bossmonster.user;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

	@Id
    @Size(min = 5, max = 20)
	String username;

    @NotEmpty
    @Size(min = 3, max = 20)
    String nickname;

    @NotEmpty
    @Email
    String email;

    @NotEmpty
    @Size(min = 3, max = 100)
    String description;

    String avatar;

    @NotEmpty
    @Size(min = 6, max = 20)
	String password;

    boolean enabled;
    
    @ManyToMany
    private List<GameResult> results;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;
}
