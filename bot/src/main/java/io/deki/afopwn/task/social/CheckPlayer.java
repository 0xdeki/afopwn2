package io.deki.afopwn.task.social;

import com.google.gson.JsonObject;
import io.deki.afopwn.dto.AvatarInfo;
import io.deki.afopwn.task.Task;

public class CheckPlayer extends Task {

    private String username;

    private AvatarInfo info;

    public CheckPlayer(String username) {
        this.username = username;
    }

    @Override
    public String directory() {
        return "check_avatar";
    }

    @Override
    public int act() {
        return 4;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("NAME", username);
        return object;
    }

    @Override
    public void response(JsonObject object) {
        if (object.has("MAIN") && object.getAsJsonObject("MAIN").has("FRD")) {
            info = new AvatarInfo();
            handleAvatarInfo(object.getAsJsonObject("MAIN").getAsJsonObject("FRD"), info);
        }
    }

    public AvatarInfo getInfo() {
        return info;
    }
}
