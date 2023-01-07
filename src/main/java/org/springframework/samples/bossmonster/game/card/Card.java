package org.springframework.samples.bossmonster.game.card;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@NoArgsConstructor
public class Card {

    @Transient
    public static final Card DISCARD_PILE_CARD = new Card("Discard Pile Cover","/resources/images/discard_pile.png");
    @Transient
    public static final Card ROOM_PILE_CARD = new Card("Room Pile Cover","/resources/images/rooms/face_down.png");
    @Transient
    public static final Card HERO_PILE_CARD = new Card("Hero Pile Cover","/resources/images/heroes/hero_pile.png");
    @Transient
    public static final Card CITY_PILE_CARD = new Card("City Pile Cover","/resources/images/city.png");

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false)
    private Integer id;
    String name;
    String cardImage;

    public Card(String name, String cardImage) {
        this.name = name;
        this.cardImage = cardImage;
    }

    public String toString() {
        return this.name;
    }
}
