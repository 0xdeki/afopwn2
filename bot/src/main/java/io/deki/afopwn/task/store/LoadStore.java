package io.deki.afopwn.task.store;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.deki.afopwn.dto.StoreItem;
import io.deki.afopwn.task.Task;

import java.util.ArrayList;
import java.util.List;

public class LoadStore extends Task {

    private List<StoreItem> items = new ArrayList<>();

    @Override
    public String directory() {
        return "store";
    }

    @Override
    public int act() {
        return 0;
    }

    @Override
    public JsonObject construct() {
        return null;
    }

    @Override
    public void response(JsonObject object) {
        if (!object.has("MAIN") || !object.getAsJsonObject("MAIN").has("ITEMS")) {
            return;
        }

        for (JsonElement listing : object.getAsJsonObject("MAIN").getAsJsonArray("ITEMS")) {
            StoreItem item = new StoreItem();
            item.parse(listing.getAsJsonObject());
            getItems().add(item);
        }
    }

    public List<StoreItem> getItems() {
        return items;
    }
}
