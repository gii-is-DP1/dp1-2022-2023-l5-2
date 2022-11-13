package org.springframework.samples.bossmonster.user;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.bossmonster.gameResult.GameResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
	@Id
	String username;
    String nickname;
    String email;
    String description;
    String avatar;
	String password;
    boolean enabled;
    
    @ManyToMany(mappedBy="participants")
    private Set<GameResult> results;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;
}
