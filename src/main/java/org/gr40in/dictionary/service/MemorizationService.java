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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public List<MemorizationDto> findAllByUserId(Long userId) {
        Pageable pageable = PageRequest.of(0, 100);
        return memorizationRepository.findALLByUserId(userId, pageable)
                .stream()
                .map(memorizationMapper::toDto)
                .toList();
    }

    public List<MemorizationDto> getAll() {
        if (memorizationRepository.findAll().isEmpty()) {
            return new ArrayList<>();
        }
        return memorizationRepository.findAll()
                .stream()
                .map(memorizationMapper::toDto)
                .toList();
    }

    public List<MemorizationDto> getRandomMemorizationDtos() {
        Random random = new Random();
        var max = memorizationRepository.count();
        List<Long> randomIds = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) randomIds.add(random.nextLong(max));
        var list = memorizationRepository.findAllById(randomIds);
        if (list.isEmpty()) return new ArrayList<>();
        return list.stream()
                .map(memorizationMapper::toDto)
                .toList();
    }

    public Boolean checkChoice(String engExp, String rusExp) {
        var choosed = translationRepository.findByEnglishExpression(engExp);
        return choosed.map(translation -> translation.getRussianExpression().equals(rusExp)).orElse(false);
    }
}
