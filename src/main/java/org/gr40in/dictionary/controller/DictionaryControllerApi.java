package org.gr40in.dictionary.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class DictionaryControllerApi {
    private static final Logger log = LoggerFactory.getLogger(DictionaryControllerApi.class);
    private final DictionaryService service;

    @RequestMapping(value = "translate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @PostMapping
    public ResponseEntity<TranslationDto> test(TranslationDto translationDto)  {
        log.info("@RequestMapping(value = \"translate\",method = RequestMethod.GET)");

        return ResponseEntity
                .ok()
                .body(service.createTranslation(translationDto));
    }


//    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
//    public String welcomepage() {
//        return "Welcome to Yawin Tutor\n";
//    }


}
