package com.carsales.controllers;

import com.carsales.controllers.Main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCont extends Attributes {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("role", getRole());
        return "about";
    }

    @GetMapping
    public String index1(Model model) {
        model.addAttribute("role", getRole());
        return "index";
    }

    @GetMapping("/index")
    public String index2(Model model) {
        model.addAttribute("role", getRole());
        return "index";
    }
}