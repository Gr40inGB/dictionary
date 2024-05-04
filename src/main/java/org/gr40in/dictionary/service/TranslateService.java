package org.gr40in.dictionary.service;

public interface TranslateService {
    String getEnglishTranslate(String russianExpression);

    String getRussianTranslate(String englishExpression);
}
