package io.deki.afopwn.task.land;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class StartLandProduction extends Task {

    private int index;

    public StartLandProduction(int index) {
        this.index = index;
    }

    @Override
    public String directory() {
        return "land";
    }

    @Override
    public int act() {
        return 1;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("IDX", index);
        object.addProperty("CAT", 3);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
