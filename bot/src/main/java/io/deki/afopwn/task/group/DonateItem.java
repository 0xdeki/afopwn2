package io.deki.afopwn.task.group;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class DonateItem extends Task {

    private String groupId;

    private String item;

    private int amount;

    public DonateItem(String groupId, String item, int amount) {
        this.groupId = groupId;
        this.item = item;
        this.amount = amount;
    }

    @Override
    public String directory() {
        return "grphs";
    }

    @Override
    public int act() {
        return 0;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("ID", groupId);
        object.addProperty("CNT", amount);
        object.addProperty("ITEM", item);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
