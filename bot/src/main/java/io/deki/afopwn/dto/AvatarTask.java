package io.deki.afopwn.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AvatarTask {

    private String name, description;

    private String key;

    private int id;

    private int progress;

    private int exp, gold, diamonds;

}
