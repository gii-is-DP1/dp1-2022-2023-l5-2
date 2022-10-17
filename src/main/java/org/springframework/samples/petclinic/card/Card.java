package org.springframework.samples.petclinic.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cards")
public class Card extends NamedEntity {

    @Column(name = "cardImages")
    @NotEmpty
    private String cardImage;

    @Column(name = "names")
    @NotEmpty
    private String name;
}
