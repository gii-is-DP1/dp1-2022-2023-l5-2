package org.springframework.samples.petclinic.player;

import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.samples.petclinic.card.Card;

@Getter
@Setter
public class Player {
    
    private User user;
    private Integer health;
    private Integer souls;
    private List<Card> hand;
    // private Dungeon dungeon;  //TODO

}
