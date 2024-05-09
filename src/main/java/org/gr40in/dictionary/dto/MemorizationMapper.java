package org.gr40in.dictionary.dto;

import lombok.RequiredArgsConstructor;
import org.gr40in.dictionary.dao.Memorization;
import org.gr40in.dictionary.dao.Translation;
import org.gr40in.dictionary.repository.TranslationRepository;
import org.gr40in.dictionary.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MemorizationMapper {
    private final TranslationRepository translationRepository;
    private final UserRepository userRepository;

    public MemorizationDto toDto(Memorization memorization) {
        return MemorizationDto.builder()
                .id(memorization.getId())
                .translation_id(memorization.getTranslation().getId())
                .translationEngExp(memorization.getTranslation().getEnglishExpression())
                .translationEngTrans(memorization.getTranslation().getEnglishTranscription())
                .translationRusExp(memorization.getTranslation().getRussianExpression())
                .user_id(memorization.getUser().getId())
                .user(memorization.getUser().getName())
                .initDate(memorization.getInitDate())
                .successAttempts(memorization.getSuccessAttempts())
                .build();
    }

    public Memorization toEntity(MemorizationDto memorizationDto) {
        return Memorization.builder()
                .id(memorizationDto.getId())
                .translation(translationRepository.findById(memorizationDto.translation_id).get())
                .user(userRepository.findById(memorizationDto.user_id).get())
                .initDate(memorizationDto.getInitDate())
                .successAttempts(memorizationDto.getSuccessAttempts())
                .build();
    }

}
