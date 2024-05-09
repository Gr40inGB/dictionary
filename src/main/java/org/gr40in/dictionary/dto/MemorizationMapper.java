package org.gr40in.dictionary.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dao.Memorization;
import org.gr40in.dictionary.dao.Translation;
import org.gr40in.dictionary.repository.TranslationRepository;
import org.gr40in.dictionary.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
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
        try {
            return Memorization.builder()
//                    .id(memorizationDto.getId())
                    .translation(translationRepository.findById(memorizationDto.translation_id).get())
                    .user(userRepository.findById(memorizationDto.user_id).get())
                    .initDate(memorizationDto.getInitDate())
                    .successAttempts(memorizationDto.getSuccessAttempts())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
