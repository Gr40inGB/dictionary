package org.gr40in.dictionary.service;

import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.v3.Translation;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(2)
public class GoogleTranslateService implements TranslateService {

    private String getTranslation(String englishExpression) throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://google-translate1.p.rapidapi.com/language/translate/v2")
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "ddb5959025msh5b1dbd31cef3042p15fbfcjsn9d759751bca9")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .body("q=" + englishExpression + "&target=ru&source=en")
                .asString();
        return response.getBody();
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
