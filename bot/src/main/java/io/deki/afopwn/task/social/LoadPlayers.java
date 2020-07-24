package io.deki.afopwn.task.social;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.deki.afopwn.dto.AvatarInfo;
import io.deki.afopwn.task.Task;

import java.util.ArrayList;
import java.util.List;

public class LoadPlayers extends Task {

    private List<AvatarInfo> players = new ArrayList<>();

    private int index;

    public LoadPlayers(int index) {
        this.index = index;
    }

    @Override
    public String directory() {
        return "social";
    }

    @Override
    public int act() {
        return 25;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("LOC", "ALL");
        object.addProperty("IDX", index);
        return object;
    }

    @Override
    public void response(JsonObject object) {
        if (!object.has("MAIN") || !object.getAsJsonObject("MAIN").has("RANK")) {
            return;
        }
        for (JsonElement data : object.getAsJsonObject("MAIN").getAsJsonArray("RANK")) {
            AvatarInfo info = new AvatarInfo();
            handleAvatarInfo(data.getAsJsonObject(), info);
            getPlayers().add(info);
        }
    }

    public List<AvatarInfo> getPlayers() {
        return players;
    }
}
