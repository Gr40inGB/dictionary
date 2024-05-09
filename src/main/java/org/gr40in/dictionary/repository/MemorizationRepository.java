package org.gr40in.dictionary.repository;

import org.gr40in.dictionary.dao.Memorization;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemorizationRepository extends JpaRepository<Memorization, Long> {
    Page<Memorization> findTop10ByUserIdOrderByInitDateDesc(Long userId, Pageable pageable);

    Optional<Memorization> findByUserIdAndTranslationId(Long userId, Long translationId);

}