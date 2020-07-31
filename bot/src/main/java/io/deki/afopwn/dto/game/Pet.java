package io.deki.afopwn.dto.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pet {

    private int id;

    private int hp, speed, agility, strength;

    private int quality;

}
