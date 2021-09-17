package com.b106.aipjt.domain.redishash;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@RedisHash("room")
public class Room {
    @Id
    private String id;
    private Member superUser;
    private boolean isStart = false;
    private int round = 0;
    private int maxRound = 3;
    private int roundTime = 60;
    private List<Member> memberList = new ArrayList<>();


    public Room(String id, Member superUser) {
        this.id = id;
        this.superUser = superUser;
        this.memberList.add(superUser);
    }
}
