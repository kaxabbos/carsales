package com.carsales.controllers;

import com.carsales.controllers.Main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartCont extends Attributes {
    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("user", getUser());
        model.addAttribute("role", getRole());
        return "cart";
    }
}
