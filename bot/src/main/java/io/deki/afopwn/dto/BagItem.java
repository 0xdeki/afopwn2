package io.deki.afopwn.dto;

import io.deki.afopwn.cache.item.Asset;
import io.deki.afopwn.repository.RepositoryContext;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BagItem {

    private int type;

    private int id;

    private int amount;

    private int special;

    public Asset getAsset() {
        for (Asset asset : RepositoryContext.cache.getAssets()) {
            if (asset.getType() == getType() && asset.getId() == getId()) {
                return asset;
            }
        }
        return null;
    }

    public String format() {
        return getType() + ":" + getId() + ":" + getAmount() + ":" + getSpecial();
    }

}
