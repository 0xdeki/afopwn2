package io.deki.afopwn.task.task;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class DailyTask extends Task {

    @Override
    public String directory() {
        return "task";
    }

    @Override
    public int act() {
        return 6;
    }

    @Override
    public JsonObject construct() {
        return null;
    }

    @Override
    public void response(JsonObject object) {

    }
}
