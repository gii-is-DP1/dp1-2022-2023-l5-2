package org.springframework.samples.bossmonster.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{

	@ManyToOne
	@JoinColumn(name = "username")
	User user;

	@Size(min = 3, max = 50)
	String authority;


}
