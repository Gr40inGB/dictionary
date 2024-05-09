package org.gr40in.dictionary.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.security.AppUserDetails;
import org.gr40in.dictionary.service.DictionaryService;
import org.gr40in.dictionary.service.MemorizationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private Long currentUserId;

    @PostConstruct
    public void init() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            currentUserId = ((AppUserDetails) authentication.getPrincipal()).getId();
        } else {
            currentUserId = null;
        }
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return ((AppUserDetails) authentication.getPrincipal()).getId();
        }
        return null;
    }

    @GetMapping()
    public String newTranslate(Model model) {
        if (!model.containsAttribute("translate_current"))
            model.addAttribute("translate_current", new TranslationDto());
        model.addAttribute("style.css", "static/style.css");
        model.addAttribute("memorizations", memorizationService.findLast10ByUserId(getCurrentUserId()));
        return "index";
    }


    @PostMapping("no")
    public String translated(@ModelAttribute("translate_current") TranslationDto translationDto, Model model) {
        var translatedDto = dictionaryService.createTranslation(translationDto);
        translationDto.setEnglishExpression(translatedDto.getEnglishExpression());
        translationDto.setRussianExpression(translatedDto.getRussianExpression());
        translationDto.setId(translatedDto.getId());
        return "index";
    }

    @PostMapping("memorized")
    public String createTranslate(
            @ModelAttribute TranslationDto translationDto, RedirectAttributes redirectAttributes,
            Model model) {
        log.info("createTranslate {}", translationDto);
        memorizationService.create(translationDto, getCurrentUserId());
        return "redirect:/translate";
    }

    @PostMapping("trans")
    public String getTranslation(@ModelAttribute TranslationDto translationDto, Model model) {
        translationDto = dictionaryService.createTranslation(translationDto);
        model.addAttribute("translate_current", translationDto);
        model.addAttribute("translations", memorizationService.findLast10ByUserId(getCurrentUserId()));
        return "index";
    }
}
