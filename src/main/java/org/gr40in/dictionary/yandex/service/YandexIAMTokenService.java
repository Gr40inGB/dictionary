package org.gr40in.dictionary.yandex.service;

import lombok.RequiredArgsConstructor;
import org.gr40in.dictionary.yandex.dao.YandexIAMToken;
import org.gr40in.dictionary.yandex.dto.YandexPassportOauthToken;
import org.gr40in.dictionary.yandex.repository.YandexIAMTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class YandexIAMTokenService {
    private final YandexIAMTokenRepository repository;

    private final String OAUTH_TOKEN = "y0_AgAAAAAQhq0fAATuwQAAAAEDjzSzAABvD1vfUINFBLu-z8yBR02IX3C_4Q";
    private final String BASE_URI = "https://iam.api.cloud.yandex.net/iam/v1/tokens";


    @Scheduled(cron = "0 0 * * * *") // Runs every hour at the beginning of the hour
    public void refreshAIMToken() {
        var newAccessToken = getOnlineFreshAccessToken();
        repository.save(newAccessToken);
    }

    public YandexIAMToken getOnlineFreshAccessToken() {
        YandexPassportOauthToken oauthToken = YandexPassportOauthToken.builder()
                .yandexPassportOauthToken(OAUTH_TOKEN)
                .build();
        RestClient restClient = RestClient.create();
        return restClient
                .post()
                .uri(BASE_URI)
                .body(oauthToken)
                .retrieve()
                .body(YandexIAMToken.class);
    }

    private YandexIAMToken getOfflineOrOnlineFreshAccessToken() {
        var token = repository.findFirstByOrderByExpiresAtDesc();
        return token.orElseGet(this::getOnlineFreshAccessToken);
    }

    public String getFreshAccessTokenBearer() {
        return "Bearer " + getOfflineOrOnlineFreshAccessToken()
                .getIamToken();
    }
}
