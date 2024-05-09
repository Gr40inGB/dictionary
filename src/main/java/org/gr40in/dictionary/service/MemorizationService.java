package org.gr40in.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dao.Memorization;
import org.gr40in.dictionary.dto.MemorizationDto;
import org.gr40in.dictionary.dto.MemorizationMapper;
import org.gr40in.dictionary.repository.MemorizationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemorizationService {
    private final MemorizationRepository memorizationRepository;
    private final MemorizationMapper memorizationMapper;

    public List<MemorizationDto> findLast10ByUserId(Long userId) {
        Pageable pageable = PageRequest.of(0, 10); // Page 0, with 10 records per page
        return memorizationRepository.findTop10ByUserIdOrderByInitDateDesc(userId, pageable)
                .stream()
                .map(memorizationMapper::toDto)
                .toList();
    }

}
