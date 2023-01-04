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

    @NotEmpty
    private String description;

    @NotEmpty
    private String image;

    @NotNull
    private Integer threshold;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Metric metric; 
}
