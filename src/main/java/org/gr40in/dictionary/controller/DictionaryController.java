package org.gr40in.dictionary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dao.User;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.service.DictionaryService;
import org.gr40in.dictionary.service.MemorizationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("translate")
public class DictionaryController {

    private final DictionaryService dictionaryService;
    private final MemorizationService memorizationService;
    private final User currentUser;

    @GetMapping
    public String newTranslate(Model model) {
        if (!model.containsAttribute("translate_current"))
            model.addAttribute("translate_current", new TranslationDto());
        model.addAttribute("translations", memorizationService.findLast10ByUserId(currentUser.getId()));
        return "index";
    }


    @PostMapping
    public String translated(@ModelAttribute("translate_current") TranslationDto translationDto, Model model) {
        var translatedDto = dictionaryService.createTranslation(translationDto);
        translationDto.setEnglishExpression(translatedDto.getEnglishExpression());
        translationDto.setRussianExpression(translatedDto.getRussianExpression());
//        model.addAttribute("translate_current", new TranslationDto());
//        model.addAttribute("translations", dictionaryService.findLast10Translations(1L));
        return "index";
    }

    @PostMapping("create")
    public String createTranslate(
            @RequestParam(name = "action_type", required = false) String translateAction,
            @RequestParam(name = "action_add", required = false) String addAction,
            @ModelAttribute TranslationDto translationDto, RedirectAttributes redirectAttributes,
            Model model) {

        translationDto = dictionaryService.createTranslation(translationDto);
        log.info("Translation created: {}", translationDto);

        if ("Translate".equals(translateAction)) {
            log.error("Translation action is 'Translate'");
//            model.addAttribute(redirectAttributes);
//            model.addAttribute("translations", dictionaryService.findLast10Translations(1L));
            return newTranslate(model);
        } else if ("Add".equals(translateAction)) {
            log.error("Add action is 'Add'");
            model.addAttribute("translate_current", translationDto);
            model.addAttribute("translations", memorizationService.findLast10ByUserId(currentUser.getId()));
            return "redirect:/translate";
        }

//        redirectAttributes.addFlashAttribute("message", "Translation created successfully");
        return "redirect:/translate";
    }

    @PostMapping("trans")
    public String getTranslation(@ModelAttribute TranslationDto translationDto, Model model) {
        translationDto = dictionaryService.createTranslation(translationDto);
        model.addAttribute("translate_current", translationDto);
        model.addAttribute("translations", memorizationService.findLast10ByUserId(currentUser.getId()));
        return "index";
    }
}
