package io.deki.afopwn.task.group;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class JoinGroup extends Task {

    private String id;

    public JoinGroup(String id) {
        this.id = id;
    }

    @Override
    public String directory() {
        return "group";
    }

    @Override
    public int act() {
        return 1;
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
