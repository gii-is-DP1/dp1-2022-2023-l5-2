package org.springframework.samples.petclinic.card;

import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Card extends NamedEntity {

    private String cardImage;
    
}