package io.deki.afopwn.task.inventory;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class SellItem extends Task {

    private String item;

    public SellItem(String item) {
        this.item = item;
    }

    @Override
    public String directory() {
        return "avatar";
    }

    @Override
    public int act() {
        return 7;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("ITEM", item);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
