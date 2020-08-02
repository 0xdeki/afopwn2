package io.deki.afopwn.task.land;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class FightLandRival extends Task {

    @Override
    public String directory() {
        return "land";
    }

    @Override
    public int act() {
        return 11;
    }

    @Override
    public JsonObject construct() {
        return null;
    }

    @Override
    public void response(JsonObject object) {

    }
}
