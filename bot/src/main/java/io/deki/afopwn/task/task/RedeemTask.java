package io.deki.afopwn.task.task;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class RedeemTask extends Task {

    private String task;

    public RedeemTask(String task) {
        this.task = task;
    }

    @Override
    public String directory() {
        return "task";
    }

    @Override
    public int act() {
        return 0;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("TSK", task);
        return object;
    }

    @Override
    public void response(JsonObject object) {
    }
}
