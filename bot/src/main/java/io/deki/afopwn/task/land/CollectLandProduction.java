package io.deki.afopwn.task.land;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class CollectLandProduction extends Task {

    private int index;

    public CollectLandProduction(int index) {
        this.index = index;
    }

    @Override
    public String directory() {
        return "land";
    }

    @Override
    public int act() {
        return 5;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("IDX", index);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
