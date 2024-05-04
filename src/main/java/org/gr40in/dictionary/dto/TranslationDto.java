package org.gr40in.dictionary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationDto {
    private Long id;
    private String englishExpression;
    private String russianExpression;
    private String englishTranscription;
    private Integer successAttempts;
    private LocalDateTime initDate;
}
