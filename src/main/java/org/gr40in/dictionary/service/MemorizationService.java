package org.gr40in.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.gr40in.dictionary.dao.Memorization;
import org.gr40in.dictionary.dto.MemorizationDto;
import org.gr40in.dictionary.dto.MemorizationMapper;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.repository.MemorizationRepository;
import org.gr40in.dictionary.repository.TranslationRepository;
import org.gr40in.dictionary.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemorizationService {
    private final MemorizationRepository memorizationRepository;
    private final MemorizationMapper memorizationMapper;
    private final UserRepository userRepository;
    private final TranslationRepository translationRepository;

    public List<MemorizationDto> findLast10ByUserId(Long userId) {
        Pageable pageable = PageRequest.of(0, 10); // Page 0, with 10 records per page
        return memorizationRepository.findTop10ByUserIdOrderByInitDateDesc(userId, pageable)
                .stream()
                .map(memorizationMapper::toDto)
                .toList();
    }

    public MemorizationDto create(TranslationDto translationDto, Long userId) {

        var memorization =
                memorizationRepository.findByUserIdAndTranslationId(
                        userId,
                        translationDto.getId());
        if (memorization.isPresent())
            return memorizationMapper.toDto(memorization.get());

        var memorizationEntity = Memorization.builder()
                .user(userRepository.findById(userId).get())
                .translation(translationRepository.findById(translationDto.getId()).get())
                .initDate(LocalDateTime.now())
                .successAttempts(0)
                .build();
        memorizationRepository.save(memorizationEntity);

        return memorizationMapper.toDto(memorizationEntity);
    }

}