package io.deki.afopwn.task.inventory;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class UseItem extends Task {

    private String item;

    public UseItem(String item) {
        this.item = item;
    }

    @Override
    public String directory() {
        return "use_item";
    }

    @Override
    public int act() {
        return 0;
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
