package com.carsales.controllers;

import com.carsales.controllers.Main.Attributes;
import com.carsales.models.enums.Role;
import com.carsales.models.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginRegCont extends Attributes {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("role", getRole());
        return "login";
    }

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("role", getRole());
        return "reg";
    }

    @PostMapping("/reg")
    public String addUser(Model model, @RequestParam String username, @RequestParam String password) {
        if (repoUsers.findAll().size() == 0 || repoUsers.findAll().isEmpty()) {
            repoUsers.save(new Users(username, password, Role.ADMIN));
            return "redirect:/login";
        }
        Users userFromDB = repoUsers.findByUsername(username);
        if (userFromDB != null) {
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Пользователь c таким именем уже существует существует!");
            return "reg";
        }
        repoUsers.save(new Users(username, password, Role.USER));

        return "redirect:/login";
    }
}
