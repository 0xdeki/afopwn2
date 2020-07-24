package io.deki.afopwn.task.arena;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.deki.afopwn.dto.AvatarInfo;
import io.deki.afopwn.task.Task;

import java.util.ArrayList;
import java.util.List;

public class LoadRivals extends Task {

    private List<AvatarInfo> rivals = new ArrayList<>();

    @Override
    public String directory() {
        return "list_rival";
    }

    @Override
    public int act() {
        return 0;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("CNT", 30);
        object.addProperty("LV", getAccount().getAvatarInfo().getLevel());
        return object;
    }

    @Override
    public void response(JsonObject object) {
        if (!object.has("MAIN") || !object.getAsJsonObject("MAIN").has("RVL")) {
            return;
        }
        for (JsonElement rival : object.getAsJsonObject("MAIN").getAsJsonArray("RVL")) {
            AvatarInfo info = new AvatarInfo();
            handleAvatarInfo(rival.getAsJsonObject(), info);
            getRivals().add(info);
        }
    }

    public List<AvatarInfo> getRivals() {
        return rivals;
    }
}
