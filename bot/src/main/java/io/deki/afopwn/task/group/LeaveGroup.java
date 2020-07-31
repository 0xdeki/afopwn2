package io.deki.afopwn.task.group;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class LeaveGroup extends Task {

    @Override
    public String directory() {
        return "group";
    }

    @Override
    public int act() {
        return 2;
    }

    @Override
    public JsonObject construct() {
        return null;
    }

    @Override
    public void response(JsonObject object) {

    }
}
