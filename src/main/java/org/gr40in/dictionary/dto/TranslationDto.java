package org.gr40in.dictionary.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranslationDto {
    private Long id;
    private String englishExpression;
    private String russianExpression;
    private String englishTranscription;
    private Integer successAttempts;
    private LocalDateTime initDate;
}
