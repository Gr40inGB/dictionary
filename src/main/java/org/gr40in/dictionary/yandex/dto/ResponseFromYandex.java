package org.gr40in.dictionary.yandex.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Data
public class ResponseFromYandex {
    private ArrayList<Translations> translations;
}
