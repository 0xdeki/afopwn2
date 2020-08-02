package io.deki.afopwn.task.land;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class BuyLand extends Task {

    private int type;

    public BuyLand(int type) {
        this.type = type;
    }

    @Override
    public String directory() {
        return "land";
    }

    @Override
    public int act() {
        return 9;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("IDX", getAccount().getAvatarInfo().getIndexOfEmptyLand());
        object.addProperty("TYPE", type);
        object.addProperty("CTYPE", 13);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
