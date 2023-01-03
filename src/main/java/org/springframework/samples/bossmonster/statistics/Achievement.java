package org.springframework.samples.bossmonster.statistics;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
<<<<<<< HEAD
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
=======
import javax.validation.constraints.NotEmpty;
>>>>>>> abd7c7536d5876e8c7e8f41806388122b21e2b19

import org.springframework.samples.bossmonster.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Achievement extends NamedEntity{
<<<<<<< HEAD
    @NotNull
=======

    @NotEmpty
>>>>>>> abd7c7536d5876e8c7e8f41806388122b21e2b19
    private String description;

    @NotEmpty
    private String image;
<<<<<<< HEAD
    @Min(value = 1)
=======

    @NotEmpty
>>>>>>> abd7c7536d5876e8c7e8f41806388122b21e2b19
    private Integer threshold;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Metric metric; 
}
