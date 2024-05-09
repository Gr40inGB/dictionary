package org.gr40in.dictionary.service;

import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.v3.Translation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
@Order(3)
public class GoogleTranslateService implements TranslateService {

    private String getTranslation(String englishExpression) {

        RestClient restClient = RestClient.create();
        var responseBody = restClient
                .post()
                .uri("https://google-translate1.p.rapidapi.com/language/translate/v2")
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "ddb5959025msh5b1dbd31cef3042p15fbfcjsn9d759751bca9")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .body("q=" + englishExpression + "&target=ru&source=en");

        var response =
                responseBody.retrieve()
//                .onStatus(s -> s.value() ==200,)
                        .onStatus(s -> s.value() == 404, (req, res) -> {
                            throw new RuntimeException("Resource not found");
                        }).
                        onStatus(s -> s.value() >= 500, (req, res) -> {
                            throw new RuntimeException("Server Error");
                        })
                        //TO_DO
                        .toString();

//        if (response.getTranslations() == null || response.getTranslations().isEmpty()) {
//            log.error("Yandex translation fail" + request.getSourceLanguageCode()
//                    + ":" + request.getTargetLanguageCode()
//                    + " : " + Arrays.toString(request.getTexts()));
//            return null;
//        }
//        return response.getTranslations().stream()
//                .map(Translations::getText)
//                .collect(Collectors.joining(" "));

        return response;
    }

    @Override
    public String getEnglishTranslate(String russianExpression) {
        return null;
    }

    @Override
    public String getRussianTranslate(String englishExpression) {
        log.error("GoogleTranslateService work");
        return null;
    }
}
