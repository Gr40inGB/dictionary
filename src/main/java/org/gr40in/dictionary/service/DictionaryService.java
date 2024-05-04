package org.gr40in.dictionary.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.net.httpserver.Request;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.gr40in.dictionary.dao.Translation;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.dto.TranslationMapper;
import org.gr40in.dictionary.dto.YandexPassportOauthToken;
import org.gr40in.dictionary.dto.YandexToken;
import org.gr40in.dictionary.repository.DictionaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

//import java.net.http.HttpResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    private final List<TranslateService> translateServices;
//    private final GoogleTranslateService googleTranslateService;
//    private final YandexTranslateService yandexTranslateService;
//    private final LocalTranslateService localTranslateService;

    private final TranslationMapper translationMapper;

    public TranslationDto createTranslation(TranslationDto dto) {
        dto.setId(null);
        var translation = translationMapper.toEntity(dto);

        String engExp = translation.getEnglishExpression();
        String rusExp = translation.getRussianExpression();

        if (!engExp.isBlank() && rusExp.isBlank()) {
            translation.setRussianExpression(getRussianTranslate(engExp));
        } else if (engExp.isBlank() && !rusExp.isBlank()) {
            translation.setEnglishExpression(getEnglishTranslate(rusExp));
        }

        dto.setInitDate(LocalDateTime.now());

        Translation save = dictionaryRepository.save(translationMapper.toEntity(dto));
        return translationMapper.toDto(save);
    }

    private String getRussianTranslate(String englishExpression) {
        for (var transService : translateServices) {
            var russianTranslate = transService.getRussianTranslate(englishExpression);
            if (russianTranslate!=null && !russianTranslate.isBlank()) return russianTranslate;
        }
        return "";
    }

    private String getEnglishTranslate(String russianExpression) {
        for (var transService : translateServices) {
            var englishTranslate = transService.getEnglishTranslate(russianExpression);
            if (!englishTranslate.isBlank()) return englishTranslate;
        }
        return "";
    }

    public TranslationDto createTranslation(String englishExpression) throws UnirestException {
        var translation = getTranslationOfExpression(englishExpression);
        var saved = dictionaryRepository.save(translation);
        return translationMapper.toDto(saved);
    }

    public List<TranslationDto> findAllTranslation() {
        return dictionaryRepository.findAll().stream()
                .map(translationMapper::toDto)
                .toList();
    }

    private Translation getTranslationOfExpression(String englishExpression) throws UnirestException {
        Translation translation = new Translation();
//        translation.setRussianExpression(test());
//        translation.setEnglishExpression(englishExpression);
//        translation.setRussianExpression(getTranslation(englishExpression));
        return translation;
    }

    public List<TranslationDto> findLast10Translations(Long userId) {
        Pageable pageable = PageRequest.of(0, 10); // Page 0, with 10 records per page
        return dictionaryRepository.findTop10ByOrderByInitDateDesc(pageable)
                .stream()
                .map(translationMapper::toDto)
                .toList();
    }


}
