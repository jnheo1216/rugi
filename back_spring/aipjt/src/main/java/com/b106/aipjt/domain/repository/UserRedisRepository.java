package com.b106.aipjt.domain.repository;

import com.b106.aipjt.domain.redishash.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRedisRepository extends CrudRepository<User, String> {
}
