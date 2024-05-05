package org.gr40in.dictionary.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@Order(3)
public class LocalTranslateService implements TranslateService {
    @Override
    public String getEnglishTranslate(String russianExpression) {
        return null;
    }

    @Override
    public String getRussianTranslate(String englishExpression) {
        log.error("LocalTranslateService work");
        return null;
    }
}
