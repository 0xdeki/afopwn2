package io.deki.afopwn.task.store;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class BuyItem extends Task {

    private int id;

    public BuyItem(int id) {
        this.id = id;
    }

    @Override
    public String directory() {
        return "store";
    }

    @Override
    public int act() {
        return 2;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("ID", id);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
