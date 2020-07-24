package io.deki.afopwn.task.arena;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.deki.afopwn.dto.AvatarInfo;
import io.deki.afopwn.task.Task;

public class FightAll extends Task {

    @Override
    public String directory() {
        return "fight_all";
    }

    @Override
    public int act() {
        return 5;
    }

    @Override
    public JsonObject construct() {
        LoadRivals rivals = new LoadRivals();
        getAccount().postTask(rivals);

        if (rivals.getRivals().size() < 1) {
            return null;
        }

        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        for (AvatarInfo rival : rivals.getRivals()) {
            array.add(rival.getKey());
        }
        object.add("DEF", array);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
