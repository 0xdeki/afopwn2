package io.deki.afopwn.task.land;

import com.google.gson.JsonObject;
import io.deki.afopwn.dto.AvatarInfo;
import io.deki.afopwn.task.Task;

public class GetLandRival extends Task {

    private AvatarInfo rival;

    @Override
    public String directory() {
        return "land";
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
        if (object.has("MAIN") && object.getAsJsonObject("MAIN").has("FRD")) {
            rival = new AvatarInfo();
            handleAvatarInfo(object.getAsJsonObject("MAIN").getAsJsonObject("FRD"), rival);
        }
    }

    public AvatarInfo getRival() {
        return rival;
    }
}
