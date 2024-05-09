package org.gr40in.dictionary.yandex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.service.TranslateService;
import org.gr40in.dictionary.yandex.dto.RequestToYandex;
import org.gr40in.dictionary.yandex.dto.ResponseFromYandex;
import org.gr40in.dictionary.yandex.dto.Translations;
import org.gr40in.dictionary.yandex.repository.YandexIAMTokenRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Slf4j
@Order(2)
@RequiredArgsConstructor
public class YandexTranslateService implements TranslateService {

    private final YandexIAMTokenRepository yandexIAMTokenRepository;
    private final YandexIAMTokenService yandexIAMTokenService;

    //    private final String FOLDER = "b1gd9mrb165uqhrlebqn";
    private final String FOLDER = "aje9icqfeq0qrdslfh1d";
    private final String OAUTH_TOKEN = "y0_AgAAAAAQhq0fAATuwQAAAAEDjzSzAABvD1vfUINFBLu-z8yBR02IX3C_4Q";
    private final String BASE_URI = "https://iam.api.cloud.yandex.net/iam/v1/tokens";
    private final String TRANSLATE_URL = "https://translate.api.cloud.yandex.net/translate/v2/translate";

    @Override
    public String getEnglishTranslate(String russianExpression) {
        var request = RequestToYandex.builder()
                .sourceLanguageCode("ru")
                .targetLanguageCode("en")
//                .texts(russianExpression.split("\\s"))
                .texts(new String[]{russianExpression})//.split("\\s"))
//                .folderId(FOLDER)
                .build();
        return getTranslate(request);
    }

    @Override
    public String getRussianTranslate(String englishExpression) {
        var request = RequestToYandex.builder()
                .sourceLanguageCode("en")
                .targetLanguageCode("ru")
                .texts(new String[]{englishExpression})//.split("\\s"))
//                .folderId(FOLDER)
                .build();
        return getTranslate(request);
    }

    private String getTranslate(RequestToYandex request) {

        RestClient restClient = RestClient.create();
        var responseBody = restClient
                .post()
                .uri(TRANSLATE_URL)
                .header("Authorization", "Api-Key " + "AQVNyZM092ga23lXxC3CKt1IYMa_CwRdL1KGynoq")
                .body(request);
        var response =
                responseBody.retrieve()
//                .onStatus(s -> s.value() ==200,)
                        .onStatus(s -> s.value() == 404, (req, res) -> {
                            throw new RuntimeException("Resource not found");
                        }).
                        onStatus(s -> s.value() >= 500, (req, res) -> {
                            throw new RuntimeException("Server Error");
                        })
                        .body(ResponseFromYandex.class);

        if (response.getTranslations() == null || response.getTranslations().isEmpty()) {
            log.error("Yandex translation fail" + request.getSourceLanguageCode()
                    + ":" + request.getTargetLanguageCode()
                    + " : " + Arrays.toString(request.getTexts()));
            return null;
        }
        return response.getTranslations().stream()
                .map(Translations::getText)
                .collect(Collectors.joining(" "));
    }


}
