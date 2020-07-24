package io.deki.afopwn.dto;

import io.deki.afopwn.cache.item.Asset;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AvatarInfo {

    private String name;

    private String key;

    private long lastLogin;

    private long nextDailyReward;

    private int level, exp;

    private int hp, speed, agility, strength;

    private int skin;

    private int energy;

    private int gold, diamonds;

    private List<ExploreMap> maps;

    private List<BagItem> bag;

    private List<BagItem> equipment;

    private List<BagItem> weapons;

    private List<Asset> skills;

    private List<AvatarTask> tasks;

    public Optional<ExploreMap> getMap(int id) {
        for (ExploreMap map : getMaps()) {
            if (map.getId() == id) {
                return Optional.of(map);
            }
        }
        return Optional.empty();
    }

    public Optional<BagItem> getBagItem(String name) {
         return getBagItem(item -> item.getAsset() != null && item.getAsset().getName().equalsIgnoreCase(name));
    }

    public Optional<BagItem> getEquipmentItem(String name) {
        return getEquipmentItem(item -> item.getAsset() != null && item.getAsset().getName().equalsIgnoreCase(name));
    }

    public Optional<BagItem> getWeapon(String name) {
        return getWeapon(item -> item.getAsset() != null && item.getAsset().getName().equalsIgnoreCase(name));
    }

    public Optional<Asset> getSkill(String name) {
        return getSkill(asset -> asset.getName().equalsIgnoreCase(name));
    }


    public Optional<BagItem> getBagItem(Predicate<BagItem> predicate) {
        for (BagItem bagItem : getBag()) {
            if (predicate.test(bagItem)) {
                return Optional.of(bagItem);
            }
        }
        return Optional.empty();
    }

    public Optional<BagItem> getEquipmentItem(Predicate<BagItem> predicate) {
        for (BagItem bagItem : getEquipment()) {
            if (predicate.test(bagItem)) {
                return Optional.of(bagItem);
            }
        }
        return Optional.empty();
    }

    public Optional<BagItem> getWeapon(Predicate<BagItem> predicate) {
        for (BagItem bagItem : getWeapons()) {
            if (predicate.test(bagItem)) {
                return Optional.of(bagItem);
            }
        }
        return Optional.empty();
    }

    public Optional<Asset> getSkill(Predicate<Asset> predicate) {
        for (Asset asset: getSkills()) {
            if (predicate.test(asset)) {
                return Optional.of(asset);
            }
        }
        return Optional.empty();
    }

}
