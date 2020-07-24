package io.deki.afopwn.task.contest;

import com.google.gson.JsonObject;
import io.deki.afopwn.task.Task;

public class EnrollContest extends Task {

    @Override
    public String directory() {
        return "contest";
    }

    @Override
    public int act() {
        return 0;
    }

    @Override
    public JsonObject construct() {
        return null;
    }

    @Override
    public void response(JsonObject object) {

    }
}
