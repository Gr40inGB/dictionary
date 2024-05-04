package org.gr40in.dictionary.controller;

import lombok.RequiredArgsConstructor;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.service.DictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("translate")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping
    public String newTranslate(Model model) {
        model.addAttribute("translate_current", new TranslationDto());
        model.addAttribute("translations", dictionaryService.findLast10Translations(1L));
        return "index";
    }


    @PostMapping("create")
    public String createTranslate(@ModelAttribute TranslationDto translationDto, RedirectAttributes redirectAttributes) {
        dictionaryService.createTranslation(translationDto);
        redirectAttributes.addFlashAttribute("message", "Translation created successfully");
        return "redirect:/translate";
    }
}
