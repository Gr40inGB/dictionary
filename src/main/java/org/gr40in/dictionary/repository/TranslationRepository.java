package org.gr40in.dictionary.repository;

import org.gr40in.dictionary.dao.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

//    Page<Translation> findTop10ByOrderByDesc(Pageable pageable);

    Optional<Translation> findByEnglishExpression(String englishExpression);

    Optional<Translation> findByRussianExpression(String russianExpression);
}
