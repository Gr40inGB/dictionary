package org.gr40in.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.repository.TranslationRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Order(1)
public class LocalTranslateService implements TranslateService {

    private final TranslationRepository translationRepository;

    @Override
    public String getEnglishTranslate(String russianExpression) {
        var translations = translationRepository.findByRussianExpression(russianExpression);
        return translations.isPresent() ? translations.get().getEnglishExpression() : "";
    }

    @Override
    public String getRussianTranslate(String englishExpression) {
        var translations = translationRepository.findByRussianExpression(englishExpression);
        return translations.isPresent() ? translations.get().getRussianExpression() : "";
    }
}
