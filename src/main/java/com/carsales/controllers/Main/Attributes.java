package com.carsales.controllers.Main;

import org.springframework.ui.Model;

public class Attributes extends Main {
    protected void AddAttributes(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }

    protected void AddAttributesDealershipEdit(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("dealership", repoDealerships.getReferenceById(id));
    }

}
