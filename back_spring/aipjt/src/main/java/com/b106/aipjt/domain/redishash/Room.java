package com.b106.aipjt.domain.redishash;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@RedisHash("room")
@ToString(of = {"id", "superUser"})
public class Room {
    @Id
    private String id;
    private User superUser;
    private boolean isStart = false;
    private int round = 0;
    private int maxRound = 3;
    private int roundTime = 60;
    private List<User> userList = new ArrayList<>();


    public Room(String id, User superUser) {
        this.id = id;
        this.superUser = superUser;
    }
}
