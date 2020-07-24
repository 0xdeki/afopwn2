package io.deki.afopwn.task.social;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class AddFriend extends Task {

    private String name;

    public AddFriend(String name) {
        this.name = name;
    }

    @Override
    public String directory() {
        return "social";
    }

    @Override
    public int act() {
        return 5;
    }

    @Override
    public JsonObject construct() {
        CheckPlayer player = new CheckPlayer(name);
        getAccount().postTask(player);

        if (player.getInfo().getKey() == null) {
            return null;
        }

        JsonObject object = new JsonObject();
        object.addProperty("KEY", player.getInfo().getKey());
        return object;
    }

    @Override
    public void response(JsonObject object) {

    }
}
