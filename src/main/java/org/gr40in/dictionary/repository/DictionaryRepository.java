package org.gr40in.dictionary.repository;

import org.gr40in.dictionary.dao.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictionaryRepository extends JpaRepository<Translation, Long> {

    Page<Translation> findTop10ByOrderByInitDateDesc(Pageable pageable);
}
