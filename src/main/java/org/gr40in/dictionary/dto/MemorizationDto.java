package org.gr40in.dictionary.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gr40in.dictionary.dao.Translation;
import org.gr40in.dictionary.dao.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemorizationDto {
    Long id;
    Long translation_id;
    String translationEngExp;
    String translationEngTrans;
    String translationRusExp;
    Long user_id;
    String  user;
    private LocalDateTime initDate;
    private Integer successAttempts;
}
