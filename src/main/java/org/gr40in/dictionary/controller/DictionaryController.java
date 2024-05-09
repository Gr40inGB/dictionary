package org.gr40in.dictionary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gr40in.dictionary.dto.TranslationDto;
import org.gr40in.dictionary.security.AppUserDetails;
import org.gr40in.dictionary.service.TranslationService;
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

    private final TranslationService translationService;
    private final MemorizationService memorizationService;

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetails) {
            return ((AppUserDetails) authentication.getPrincipal()).getId();
        }
        return null;
    }

    @GetMapping()
    public String newTranslate(Model model) {
        if (!model.containsAttribute("translate_current"))
            model.addAttribute("translate_current", new TranslationDto());
        model.addAttribute(
                "memorizations",
                memorizationService.findLast10ByUserId(getCurrentUserId()));
        return "index";
    }


    @PostMapping("memorized")
    public String createMemorization(
            @ModelAttribute TranslationDto translationDto, RedirectAttributes redirectAttributes,
            Model model) {
        memorizationService.create(translationDto, getCurrentUserId());
        return "redirect:/translate";
    }

}
