package io.deki.afopwn.task.store;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class BuyItems extends Task {

    private int id, amount;

    public BuyItems(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public String directory() {
        return "store";
    }

    @Override
    public int act() {
        return 7;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("ID", id);
        object.addProperty("CNT", amount);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
