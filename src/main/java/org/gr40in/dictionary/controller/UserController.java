package org.gr40in.dictionary.controller;

import lombok.RequiredArgsConstructor;
import org.gr40in.dictionary.dao.User;
import org.gr40in.dictionary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller()
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users")
    public String homePage(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users_manage";
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

//    @GetMapping("management")
//    public String getUsers(Model model) {
//        model.addAttribute("users", userService.getUsers());
//        return "users_manage";
//    }

//    @GetMapping("{id}")
//    public String getUserById(@PathVariable Long id, Model model) {
//        User userByName = userService.getUserByName(id);
//        ArrayList<User> users = new ArrayList<>();
//        users.add(userByName);
//        model.addAttribute("users", users);
//        return "users_manage";
//    }
}
