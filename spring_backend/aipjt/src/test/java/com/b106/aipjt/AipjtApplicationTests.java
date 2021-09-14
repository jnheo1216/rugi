package com.b106.aipjt;

import com.b106.aipjt.domain.redishash.Member;
import com.b106.aipjt.domain.redishash.Room;
import com.b106.aipjt.domain.repository.RoomRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class AipjtApplicationTests {

    @Autowired
    private RoomRedisRepository roomRedisRepository;

    @AfterEach
    public void deleteAllRoom() throws Exception {
        roomRedisRepository.deleteAll();
    }

	@Test
	void contextLoads() {
	}



	@Test
    void roomCreate() {
        Member member = new Member("/avatar/1", "준영");
        Room room = new Room(null, member);
        roomRedisRepository.save(room);
        System.out.println(member.getId()); // 정상적으로 id가 생성됨. 롤백은 어떻게 하는건지 모르겠음. template 써야되는 것으로 알고있음
        Optional<Room> byId = roomRedisRepository.findById(room.getId());
        if (byId.isPresent()) {
            Room findRoom = byId.get();
            // 비교한다
            System.out.println("roomID : "+room.getId());
            System.out.println("findRoomID : "+findRoom.getId());
            System.out.println("memberID : "+member.getId());
            System.out.println("findMemberID : "+findRoom.getMemberList().get(0).getId());
            Assertions.assertThat(findRoom.getId()).isEqualTo(room.getId());
        }
        // 찾지 못한 것
    }

    @Test
    void addMemberToRoom() {
        Member member1 = new Member("/avatar/1", "준영1");
        Room room = new Room(null, member1);
        roomRedisRepository.save(room);
        Optional<Room> byId = roomRedisRepository.findById(room.getId());
        if (byId.isPresent()) {
            Room findRoom = byId.get();
            Member member2 = new Member("/avatar/2", "준영2");
            findRoom.getMemberList().add(member2);
            roomRedisRepository.save(findRoom);

            Optional<Room> byId2 = roomRedisRepository.findById(room.getId());
            Assertions.assertThat(byId2.get().getMemberList().size()).isEqualTo(2);

        }
    }


    @Test
    void roomDelete() {
        List<Room> roomList = (List)roomRedisRepository.findAll();
        for (int i = 0; i < roomList.size(); i++) {
            Room room = roomList.get(i);
            roomRedisRepository.delete(room);
        }
        List<Room> newRoomList = (List)roomRedisRepository.findAll();
        Assertions.assertThat(newRoomList.size()).isEqualTo(0);
    }
}
