package org.gr40in.dictionary.yandex.service;

import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.service.TranslateService;
import org.gr40in.dictionary.yandex.dao.YandexIAMToken;
import org.gr40in.dictionary.yandex.dto.YandexPassportOauthToken;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
@Order(1)
public class YandexTranslateService implements TranslateService {

    private final String FOLDER = "b1gd9mrb165uqhrlebqn";
    private final String OAUTH_TOKEN = "y0_AgAAAAAQhq0fAATuwQAAAAEDjzSzAABvD1vfUINFBLu-z8yBR02IX3C_4Q";
    private final String BASE_URI = "https://iam.api.cloud.yandex.net/iam/v1/tokens";



    private String test() {
        YandexPassportOauthToken oauthToken = YandexPassportOauthToken.builder()
                .yandexPassportOauthToken(OAUTH_TOKEN)
                .build();

        RestClient restClient = RestClient.create();
        return restClient
                .post()
                .uri(BASE_URI)
                .body(oauthToken)
                .retrieve()
                .body(YandexIAMToken.class).getIamToken();
    }

    @Override
    public String getEnglishTranslate(String russianExpression) {
        return null;
    }

    @Override
    public String getRussianTranslate(String englishExpression) {
        log.error("YandexTranslateService work");
        return null;
    }


}
