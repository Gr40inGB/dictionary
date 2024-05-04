package org.gr40in.dictionary.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class YandexToken {
    private String iamToken;
    private LocalDateTime expiresAt;
}
