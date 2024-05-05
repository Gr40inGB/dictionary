package org.gr40in.dictionary.yandex.config;

import lombok.RequiredArgsConstructor;
import org.gr40in.dictionary.yandex.repository.YandexIAMTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YandexIAMTokenRefresher {
    private final YandexIAMTokenRepository repository;

    @Scheduled(cron = "0 0 * * * *") // Runs every hour at the beginning of the hour
    public void refreshAIMToken() {
//        var lastToken = repository
    }

}
