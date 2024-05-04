package org.gr40in.dictionary.dto;

import org.gr40in.dictionary.dao.Translation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TranslationMapper {
    public TranslationDto toDto(Translation translation) {
        return TranslationDto.builder()
                .id(translation.getId())
                .englishExpression(translation.getEnglishExpression())
                .russianExpression(translation.getRussianExpression())
                .englishTranscription(translation.getEnglishTranscription())
                .initDate(translation.getInitDate())
                .successAttempts(translation.getSuccessAttempts())
                .build();
    }

    public Translation toEntity(TranslationDto translationDto) {
        return Translation.builder()
                .id(translationDto.getId())
                .englishExpression(translationDto.getEnglishExpression())
                .russianExpression(translationDto.getRussianExpression())
                .englishTranscription(translationDto.getEnglishTranscription())
                .initDate(translationDto.getInitDate())
                .successAttempts(translationDto.getSuccessAttempts())
                .build();
    }

}
