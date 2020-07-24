package io.deki.afopwn.task.explore;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class Explore extends Task {

    private int id;

    public Explore(int id) {
        this.id = id;
    }

    @Override
    public String directory() {
        return "explore";
    }

    @Override
    public int act() {
        return 11;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("ID", id);
        object.addProperty("STEP", getAccount().getAvatarInfo().getMap(id).get().getStep());
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
