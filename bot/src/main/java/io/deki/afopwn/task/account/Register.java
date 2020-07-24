package io.deki.afopwn.task.account;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class Register extends Task {

    @Override
    public String directory() {
        return "register";
    }

    @Override
    public int act() {
        return 0;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("OS", 2);
        object.addProperty("VER", "6.6.1");
        object.addProperty("DEV", getAccount().getDeviceId());
        object.addProperty("SKIN", 8);
        object.addProperty("LANG", "en");
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }

    @Override
    public long getTime() {
        return 0;
    }
}
