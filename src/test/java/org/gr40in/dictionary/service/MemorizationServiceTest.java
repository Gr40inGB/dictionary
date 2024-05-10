package org.gr40in.dictionary.service;

import org.gr40in.dictionary.dao.Memorization;
import org.gr40in.dictionary.dao.Translation;
import org.gr40in.dictionary.dao.User;
import org.gr40in.dictionary.dto.MemorizationDto;
import org.gr40in.dictionary.dto.MemorizationMapper;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.repository.MemorizationRepository;
import org.gr40in.dictionary.repository.TranslationRepository;
import org.gr40in.dictionary.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class MemorizationServiceTest {

    @Mock
    private MemorizationRepository memorizationRepository;

    @Mock
    private MemorizationMapper memorizationMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TranslationRepository translationRepository;

    @InjectMocks
    private MemorizationService memorizationService;

    @Test
    void findLast10ByUserId() {
        // Mocking data
        Page<Memorization> memorizations = Page.empty();
        when(memorizationRepository.findTop10ByUserIdOrderByInitDateDesc(anyLong(), any()))
                .thenReturn(memorizations);
        when(memorizationMapper.toDto(any())).thenReturn(new MemorizationDto());

        // Calling the service method
        List<MemorizationDto> result = memorizationService.findLast10ByUserId(1L);

        // Assertions
        assertEquals(0, result.size());
        verify(memorizationRepository, times(1)).findTop10ByUserIdOrderByInitDateDesc(anyLong(), any());
    }

    @Test
    void create() {
                // Mocking data
        TranslationDto translationDto = new TranslationDto();
        translationDto.setId(1L);

        when(memorizationRepository.findByUserIdAndTranslationId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User())); // Mock user
        when(translationRepository.findById(anyLong())).thenReturn(Optional.of(new Translation())); // Mock translation

        Memorization memorizationEntity = Memorization.builder()
                .user(new User())
                .translation(new Translation())
                .initDate(LocalDateTime.now())
                .successAttempts(0)
                .build();
        when(memorizationRepository.save(any())).thenReturn(memorizationEntity);

        MemorizationDto memorizationDto = new MemorizationDto(); // Create DTO
        when(memorizationMapper.toDto(any())).thenReturn(memorizationDto);

        // Calling the service method
        MemorizationDto result = memorizationService.create(translationDto, 1L);

        // Assertions
        assertEquals(memorizationDto, result);
        verify(memorizationRepository, times(1)).findByUserIdAndTranslationId(anyLong(), anyLong());
        verify(userRepository, times(1)).findById(anyLong());
        verify(translationRepository, times(1)).findById(anyLong());
        verify(memorizationRepository, times(1)).save(any());
        verify(memorizationMapper, times(1)).toDto(any());
    }

@Test
    void findAllByUserId() {
        // Mocking data
        Page<Memorization> memorizations = Page.empty();
        when(memorizationRepository.findALLByUserId(anyLong(), any()))
                .thenReturn(memorizations);
        when(memorizationMapper.toDto(any())).thenReturn(new MemorizationDto());

        // Calling the service method
        List<MemorizationDto> result = memorizationService.findAllByUserId(1L);

        // Assertions
        assertEquals(0, result.size());
        verify(memorizationRepository, times(1)).findALLByUserId(anyLong(), any());
        verify(memorizationMapper, times(0)).toDto(any()); // No conversion should be called since the list is empty
    }

    @Test
    void getAll() {
        // Mocking data
        List<Memorization> memorizations = new ArrayList<>();
        when(memorizationRepository.findAll()).thenReturn(memorizations);
        when(memorizationMapper.toDto(any())).thenReturn(new MemorizationDto());

        // Calling the service method
        List<MemorizationDto> result = memorizationService.getAll();

        // Assertions
        assertEquals(0, result.size());
        verify(memorizationRepository, times(1)).findAll();
        verify(memorizationMapper, times(0)).toDto(any()); // No conversion should be called since the list is empty
    }

    @Test
    void getRandomMemorizationDtos() {
    }

    @Test
    void checkChoice() {
    }
}