package io.deki.afopwn.task.account;

import com.google.gson.JsonObject;
import io.deki.afopwn.commons.CryptoUtil;
import io.deki.afopwn.task.Task;

public class Login extends Task {

    @Override
    public String directory() {
        return "login";
    }

    @Override
    public int act() {
        return 1;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("OS", 2);
        object.addProperty("VER", "6.6.1");
        object.addProperty("DEV", getAccount().getDeviceId());
        object.addProperty("NAME", getAccount().getUsername());
        if (getAccount().getPassword() == null) {
            object.addProperty("PWD", getAccount().getPasswordHash());
        } else {
            object.addProperty("PWD", CryptoUtil.md5(getAccount().getPassword()));
        }
        object.addProperty("LANG", "en");
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
