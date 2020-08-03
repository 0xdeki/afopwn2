package io.deki.afopwn.dto;

import io.deki.afopwn.commons.Random;
import io.deki.afopwn.task.Task;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document("accounts")
public class Account {

    @Id
    private String id;

    private String username;

    @Transient
    private String sessionId;

    private String deviceId;

    private String password, passwordHash;

    private boolean main;

    private AvatarInfo avatarInfo;

    public void postTask(Task task) {
        if (getDeviceId() == null) {
            setDeviceId(Random.nextString(16));
        }
        if (getAvatarInfo() == null) {
            setAvatarInfo(new AvatarInfo());
        }

        task.setAccount(this);
        try {
            task.post();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
