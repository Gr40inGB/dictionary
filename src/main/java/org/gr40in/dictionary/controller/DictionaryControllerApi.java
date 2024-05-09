package org.gr40in.dictionary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dto.MemorizationDto;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.service.MemorizationService;
import org.gr40in.dictionary.service.TranslationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class DictionaryControllerApi {
    private final TranslationService translationService;
    private final MemorizationService memorizationService;

    @RequestMapping(value = "translate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<TranslationDto> test(TranslationDto translationDto) {
        log.info("@RequestMapping(value = \"translate\",method = RequestMethod.GET)");

        return ResponseEntity
                .ok()
                .body(translationService.createTranslation(translationDto));
    }

    @RequestMapping(value = "translate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TranslationDto>> getAllTranslations() {
        return ResponseEntity
                .ok()
                .body(translationService.findAllTranslation());
    }

    @RequestMapping(value = "memorization")
    public ResponseEntity<List<MemorizationDto>> getAllMemorizations() {
        return ResponseEntity
                .ok()
                .body(memorizationService.getAll());
    }






}
