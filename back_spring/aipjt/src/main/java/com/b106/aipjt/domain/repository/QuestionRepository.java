package com.b106.aipjt.domain.repository;

import com.b106.aipjt.domain.jpaentity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Override
    List<Question> findAll();

    @Query(value = "select * from question order by rand() limit 1", nativeQuery = true)
    Optional<Question> randomQuestion();
}
