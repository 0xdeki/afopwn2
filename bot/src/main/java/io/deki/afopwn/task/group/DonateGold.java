package io.deki.afopwn.task.group;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class DonateGold extends Task {

    private String groupId;

    private int gold;

    public DonateGold(String groupId, int gold) {
        this.groupId = groupId;
        this.gold = gold;
    }

    @Override
    public String directory() {
        return "grphs";
    }

    @Override
    public int act() {
        return 1;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("ID", groupId);
        object.addProperty("CNT", gold);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
