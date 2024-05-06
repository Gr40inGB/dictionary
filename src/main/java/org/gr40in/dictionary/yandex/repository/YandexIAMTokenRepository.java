package org.gr40in.dictionary.yandex.repository;

import org.gr40in.dictionary.yandex.dao.YandexIAMToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YandexIAMTokenRepository extends JpaRepository<YandexIAMToken, Long> {

    Optional<YandexIAMToken> findFirstByOrderByExpiresAtDesc();

}
