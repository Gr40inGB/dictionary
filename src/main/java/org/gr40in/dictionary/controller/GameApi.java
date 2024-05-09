package org.gr40in.dictionary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dao.Memorization;
import org.gr40in.dictionary.dao.Translation;
import org.gr40in.dictionary.dto.MemorizationDto;
import org.gr40in.dictionary.service.MemorizationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class GameApi {

    private final MemorizationService memorizationService;

    /**
     * Retrieves data for the game.
     *
     * @return ResponseEntity with a list of MemorizationDto objects
     */
    @RequestMapping(value = "game",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MemorizationDto>> getDataForGame() {
        return ResponseEntity
                .ok()
                .body(memorizationService.getRandomMemorizationDtos());
    }

    /**
     * Checks the choice made by the user.
     *
     * @param engExp the English expression chosen by the user
     * @param rusExp the corresponding Russian expression chosen by the user
     * @return ResponseEntity with a boolean indicating whether the choice is correct
     */
    public ResponseEntity<Boolean> checkChoice(String engExp, String rusExp) {
        return ResponseEntity
                .ok()
                .body(memorizationService.checkChoice(engExp, rusExp));
    }

}
