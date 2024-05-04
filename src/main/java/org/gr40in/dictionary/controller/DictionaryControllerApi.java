package org.gr40in.dictionary.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.service.DictionaryService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DictionaryControllerApi {
    private final DictionaryService service;

    @RequestMapping("test")
    public ResponseEntity<TranslationDto> test(@RequestBody TranslationDto translationDto) throws UnirestException {
        return ResponseEntity
                .ok()
                .body(service.createTranslation(translationDto.getEnglishExpression()));
    }


    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public String welcomepage() {
        return "Welcome to Yawin Tutor\n";
    }



}
