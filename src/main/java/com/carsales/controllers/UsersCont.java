package com.carsales.controllers;

import com.carsales.controllers.Main.Attributes;
import com.carsales.models.Users;
import com.carsales.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersCont extends Attributes {

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", repoUsers.findAll());
        model.addAttribute("roles", Role.values());
        model.addAttribute("role", getRole());
        return "users";
    }

    @PostMapping("/edit/{id}")
    public String userUpdate(@PathVariable Long id, @RequestParam Role role) {
        Users user = repoUsers.getReferenceById(id);
        user.setRole(role);
        repoUsers.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String userDelete(@PathVariable(value = "id") Long id) {
        repoUsers.deleteById(id);
        return "redirect:/users";
    }
}
