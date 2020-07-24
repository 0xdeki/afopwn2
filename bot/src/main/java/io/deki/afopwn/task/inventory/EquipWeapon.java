package io.deki.afopwn.task.inventory;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class EquipWeapon extends Task {

    private String item;

    private boolean first;

    public EquipWeapon(String item, boolean first) {
        this.item = item;
        this.first = first;
    }

    @Override
    public String directory() {
        return "use_item";
    }

    @Override
    public int act() {
        return 8;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("ITEM", item);
        object.addProperty("WAY", first ? 0 : 1);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
