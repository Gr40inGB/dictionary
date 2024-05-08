package org.gr40in.dictionary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.service.DictionaryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class DictionaryControllerApi {
    private final DictionaryService service;

    @RequestMapping(value = "translate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<TranslationDto> test(TranslationDto translationDto)  {
        log.info("@RequestMapping(value = \"translate\",method = RequestMethod.GET)");

        return ResponseEntity
                .ok()
                .body(service.createTranslation(translationDto));
    }
}
