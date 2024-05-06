package org.gr40in.dictionary.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dao.Translation;
import org.gr40in.dictionary.repository.DictionaryRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class InitDatabase {

    private final DictionaryRepository repository;

    private final String initFilePath = "src/main/resources/dictionary.txt";


    @EventListener(ContextRefreshedEvent.class)
    private void initDatabase() {

        List<Translation> list = new ArrayList<>();
        if (!repository.findAll().isEmpty()) return;



        try (BufferedReader br = new BufferedReader(new FileReader(initFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                var translation = parseLineToTranslation(line);
                list.add(translation);
            }
        } catch (IOException e) {
            log.error("PLEASE CHECK NAME OF FILE" + e.getMessage());
        }
        log.info("List have been loaded");

        repository.saveAll(list);

        log.info("Database has been loaded");
    }

    private Translation parseLineToTranslation(String line) {
        String[] parts = line.replace("\\\"", "").split(";");
        if (parts.length == 2)
            return Translation.builder()
                    .englishExpression(parts[0])
                    .russianExpression(parts[1])
                    .build();
        return Translation.builder()
                .englishExpression(parts[0])
                .englishTranscription(parts[1])
                .russianExpression(parts[2])
                .build();
    }

}
