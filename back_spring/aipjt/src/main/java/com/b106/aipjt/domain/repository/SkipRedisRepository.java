package com.b106.aipjt.domain.repository;

import com.b106.aipjt.domain.redishash.Round;
import com.b106.aipjt.domain.redishash.Skip;
import org.springframework.data.repository.CrudRepository;

public interface SkipRedisRepository extends CrudRepository<Skip, String> {
}
