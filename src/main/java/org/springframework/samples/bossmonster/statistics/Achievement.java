package org.springframework.samples.bossmonster.statistics;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.samples.bossmonster.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Achievement extends NamedEntity{
	@Id
	private Integer id;
    private String name;
    private String description;
}
