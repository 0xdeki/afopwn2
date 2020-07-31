package io.deki.afopwn.dto.game;

import com.google.gson.JsonObject;
import io.deki.afopwn.cache.item.Asset;
import io.deki.afopwn.repository.RepositoryContext;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreItem {

    private int category;

    private int itemId;

    private int storeId;

    private int goldCost, diamondCost;

    public void parse(JsonObject object) {
        if (object.has("TYPE")) {
            setCategory(object.get("TYPE").getAsInt());
        }

        if (object.has("IID")) {
            setItemId(object.get("IID").getAsInt());
        }

        if (object.has("ID")) {
            setStoreId(object.get("ID").getAsInt());
        }

        if (object.has("CST")) {
            setGoldCost(object.get("CST").getAsInt());
        }

        if (object.has("DMND")) {
            setDiamondCost(object.get("DMND").getAsInt());
        }
    }

    public Asset getAsset() {
        for (Asset asset : RepositoryContext.cache.getAssets()) {
            if (asset.getType() == getCategory() && asset.getId() == getItemId()) {
                return asset;
            }
        }
        return null;
    }

}
