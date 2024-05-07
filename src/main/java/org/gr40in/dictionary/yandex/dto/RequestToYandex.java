package org.gr40in.dictionary.yandex.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
//@Component
@Builder
@AllArgsConstructor
public class RequestToYandex {
    private String sourceLanguageCode;
    private String targetLanguageCode;
    private String[] texts;
//    private String folderId;
}
