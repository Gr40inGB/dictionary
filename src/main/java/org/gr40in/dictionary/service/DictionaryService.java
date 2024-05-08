package org.gr40in.dictionary.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dao.Translation;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.dto.TranslationMapper;
import org.gr40in.dictionary.repository.DictionaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    private final List<TranslateService> translateServices;

    private final TranslationMapper translationMapper;

    public TranslationDto createTranslation(TranslationDto dto) {
        // delete id if exists - cause id will take from database
        dto.setId(null);
        var translation = translationMapper.toEntity(dto);

        String engExp = translation.getEnglishExpression() == null ? "" : translation.getEnglishExpression();
        String rusExp = translation.getRussianExpression() == null ? "" : translation.getRussianExpression();

        // we have english - so we need to translate from eng -> to russian
        if (!engExp.isBlank() && rusExp.isBlank()) {
            translation.setRussianExpression(getRussianTranslate(engExp));
        }  // we have russian - so we need to translate from russian -> to english
        else if (engExp.isBlank() && !rusExp.isBlank()) {
            translation.setEnglishExpression(getEnglishTranslate(rusExp));
        }

        translation.setInitDate(LocalDateTime.now());

        Translation save = dictionaryRepository.save(translation);
        return translationMapper.toDto(save);
    }

    private String getRussianTranslate(String englishExpression) {
        for (var transService : translateServices) {
            var russianTranslate = transService.getRussianTranslate(englishExpression);
            if (russianTranslate != null && !russianTranslate.isBlank()) return russianTranslate;
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
