package com.b106.aipjt.domain.repository;

import com.b106.aipjt.domain.redishash.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRedisRepository extends CrudRepository<Room, String> {
}
