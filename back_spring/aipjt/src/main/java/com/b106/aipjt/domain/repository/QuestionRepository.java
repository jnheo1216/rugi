package com.b106.aipjt.domain.repository;

import com.b106.aipjt.domain.jpaentity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
