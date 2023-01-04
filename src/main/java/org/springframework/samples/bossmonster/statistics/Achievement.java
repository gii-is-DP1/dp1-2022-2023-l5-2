package org.springframework.samples.bossmonster.statistics;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.bossmonster.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Achievement extends NamedEntity{

    private String description;

    private String image;

    @NotEmpty
    private Integer threshold;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Metric metric; 
}
