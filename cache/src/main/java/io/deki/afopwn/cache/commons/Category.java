package io.deki.afopwn.cache.commons;

public enum Category {

    SKILL("Skill", 1),
    EQUIPMENT("Equipment", 2),
    WEAPON("Weapon", 3),
    SKIN("Skin", 4),
    PET("Pet", 6),
    CONFIG("Config", 8),
    CONSUMABLE("Consumable", 9),
    BUFF("Buff", 10),
    LOTTERY("Lottery", 11),
    FORGE("Forge", 12),
    MINE("Mine", 13),
    TOWER("Tower", 14),
    ACHIEVEMENT("Achievement", 15),
    EXPLORE("Explore", 16),
    MATERIAL("Material", 17),
    GAME("Game", 402),
    UNKNOWN("Unknown", -1)
    ;

    private final String name;

    private final int id;

    Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static Category findByName(String name) {
        for (Category category : values()) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return UNKNOWN;
    }

    public static Category findById(int id) {
        for (Category category : values()) {
            if (category.getId() == id) {
                return category;
            }
        }
        return UNKNOWN;
    }
}
