package io.deki.afopwn.task.arena;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class Fight extends Task {

    @Override
    public String directory() {
        return "upload_rcd";
    }

    @Override
    public int act() {
        return 3;
    }

    @Override
    public JsonObject construct() {
        LoadRivals rivals = new LoadRivals();
        getAccount().postTask(rivals);

        if (rivals.getRivals().size() < 1) {
            return null;
        }

        JsonObject object = new JsonObject();
        object.addProperty("DEF", rivals.getRivals().get(0).getKey());
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
