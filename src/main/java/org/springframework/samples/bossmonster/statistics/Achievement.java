package org.springframework.samples.bossmonster.statistics;

import javax.persistence.Entity;

import org.springframework.samples.bossmonster.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Achievement extends NamedEntity{
    private double threshold;
    private String badgeImage;
    private String description;

    public String getActualDescription(){
        return description.replace("<THRESHOLD>", String.valueOf(threshold));
    }
}
