package io.deki.afopwn.cache.item;

import io.deki.afopwn.cache.commons.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Asset {

    private int version;

    private int type;

    private int id;

    private int requiredLevel = 1;

    private int price;

    private String name;

    private String description;

    public Category getCategory() {
        return Category.findById(getType());
    }

}
