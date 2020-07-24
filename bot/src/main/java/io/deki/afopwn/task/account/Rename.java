package io.deki.afopwn.task.account;

import com.google.gson.JsonObject;
import io.deki.afopwn.commons.CryptoUtil;
import io.deki.afopwn.commons.Random;
import io.deki.afopwn.io.GenerateUsername;
import io.deki.afopwn.task.Task;
import org.apache.commons.lang3.StringUtils;

public class Rename extends Task {

    private boolean failed = false;

    @Override
    public String directory() {
        return "account";
    }

    @Override
    public int act() {
        return 0;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        if (getAccount().getUsername() == null || StringUtils.isNumeric(getAccount().getUsername())) {
            object.addProperty("NNAME", GenerateUsername.get());
        } else {
            object.addProperty("NNAME", getAccount().getUsername());
        }
        if (getAccount().getPassword() == null) {
            getAccount().setPassword(Random.nextString(12));
        }
        object.addProperty("PWD", CryptoUtil.md5(getAccount().getPassword()));
        return object;
    }

    @Override
    public void response(JsonObject object) {
        if (!object.has("MAIN") || object.getAsJsonObject("MAIN").has("ERR")) {
            failed = true;
        }
    }

    public boolean isFailed() {
        return failed;
    }
}
