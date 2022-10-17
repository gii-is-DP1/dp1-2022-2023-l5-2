package org.springframework.samples.petclinic.card;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Card extends NamedEntity {
    private String cardImage;
    private String name;
}
