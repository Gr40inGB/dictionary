package org.gr40in.dictionary.service;

import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dto.YandexPassportOauthToken;
import org.gr40in.dictionary.dto.YandexToken;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
@Order(3)
public class YandexTranslateService implements TranslateService{

        private String test() {
        YandexPassportOauthToken oauthToken = YandexPassportOauthToken.builder()
                .yandexPassportOauthToken("y0_AgAAAAAQhq0fAATuwQAAAAEDjzSzAABvD1vfUINFBLu-z8yBR02IX3C_4Q")
                .build();

        String BASE_URI = "https://iam.api.cloud.yandex.net/iam/v1/tokens";

        RestClient restClient = RestClient.create();
        return restClient
                .post()
                .uri(BASE_URI)
                .body(oauthToken)
                .retrieve()
                .body(YandexToken.class).getIamToken();
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
