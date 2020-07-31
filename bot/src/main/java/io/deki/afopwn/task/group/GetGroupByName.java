package io.deki.afopwn.task.group;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class GetGroupByName extends Task {

    private String name;

    private String id;

    public GetGroupByName(String name) {
        this.name = name;
    }

    @Override
    public String directory() {
        return "group";
    }

    @Override
    public int act() {
        return 11;
    }

    @Override
    public JsonObject construct() {
        JsonObject object = new JsonObject();
        object.addProperty("NAME", name);
        object.addProperty("CAT", 1);
        return object;
    }

    @Override
    public void response(JsonObject object) {
        if (object.has("MAIN") && object.getAsJsonObject("MAIN").has("GRP")) {
            JsonObject group = object.getAsJsonObject("MAIN").getAsJsonObject("GRP");
            if (group.has("ID")) {
                id = group.get("ID").getAsString();
            }
        }
    }

    public String getId() {
        return id;
    }
}
