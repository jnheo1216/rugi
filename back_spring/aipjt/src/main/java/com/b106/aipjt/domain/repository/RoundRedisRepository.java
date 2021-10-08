package com.b106.aipjt.domain.repository;

import com.b106.aipjt.domain.redishash.Round;
import org.springframework.data.repository.CrudRepository;

public interface RoundRedisRepository extends CrudRepository<Round, String> {
}
