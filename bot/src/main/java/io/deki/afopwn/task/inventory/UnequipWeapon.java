package io.deki.afopwn.task.inventory;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class UnequipWeapon extends Task {

    private int weaponId;

    public UnequipWeapon(int weaponId) {
        this.weaponId = weaponId;
    }

    @Override
    public String directory() {
        return "avatar";
    }

    @Override
    public int act() {
        return 9;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("WPN", weaponId);
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
