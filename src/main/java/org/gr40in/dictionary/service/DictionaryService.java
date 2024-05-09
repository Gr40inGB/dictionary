package org.gr40in.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dao.Translation;
import org.gr40in.dictionary.dto.MemorizationDto;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.dto.TranslationMapper;
import org.gr40in.dictionary.repository.TranslationRepository;
import org.gr40in.dictionary.service.exception.ServiceLayerException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final TranslationRepository translationRepository;

    private final List<TranslateService> translateServices;

    private final TranslationMapper translationMapper;

    public TranslationDto createTranslation(TranslationDto dto)  {
        // delete id if exists - cause id will take from database
        dto.setId(null);

        var translation = translationMapper.toEntity(dto);

        String engExp = translation.getEnglishExpression() == null ? "" : translation.getEnglishExpression();
        String rusExp = translation.getRussianExpression() == null ? "" : translation.getRussianExpression();

        // we have english - so we need to translate from eng -> to russian
        if (!engExp.isBlank() && rusExp.isBlank()) {
            var temp = translationRepository.findByEnglishExpression(engExp);
            if (temp.isPresent()) {
                return translationMapper.toDto(temp.get());
            }
            translation.setRussianExpression(getRussianTranslate(engExp));
        }  // we have russian - so we need to translate from russian -> to english
        else if (engExp.isBlank() && !rusExp.isBlank()) {
            var temp = translationRepository.findByRussianExpression(rusExp);
            if (temp.isPresent()) {
                return translationMapper.toDto(temp.get());
            }
            translation.setEnglishExpression(getEnglishTranslate(rusExp));
        } // if incorrect input - we'll just clean dto
        else return new TranslationDto();

//        if (translation.getEnglishExpression().isBlank() || translation.getRussianExpression().isBlank()) {
//            throw new ServiceLayerException("something wrong with translation");
//        }

        Translation save = translationRepository.save(translation);
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

    public TranslationDto createTranslation(String englishExpression) {
        var translation = getTranslationOfExpression(englishExpression);
        var saved = translationRepository.save(translation);
        return translationMapper.toDto(saved);
    }

    public List<TranslationDto> findAllTranslation() {
        return translationRepository.findAll().stream()
                .map(translationMapper::toDto)
                .toList();
    }

    private Translation getTranslationOfExpression(String englishExpression) {
        Translation translation = new Translation();
//        translation.setRussianExpression(test());
//        translation.setEnglishExpression(englishExpression);
//        translation.setRussianExpression(getTranslation(englishExpression));
        return translation;
    }

//    public List<MemorizationDto> findLast10Translations(Long userId) {
//        Pageable pageable = PageRequest.of(0, 10); // Page 0, with 10 records per page
//        return translationRepository.findTop10ByOrderByInitDateDesc(pageable)
//                .stream()
//                .map(translationMapper::toDto)
//                .toList();
//    }


}
