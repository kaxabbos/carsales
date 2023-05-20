package com.carsales.controllers;

import com.carsales.controllers.Main.Attributes;
import com.carsales.models.Dealerships;
import com.carsales.models.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/dealerships")
public class DealershipsCont extends Attributes {

    @GetMapping
    public String dealerships(Model model) {
        model.addAttribute("dealerships", repoDealerships.findAll());
        model.addAttribute("role", getRole());
        return "dealerships";
    }

    @GetMapping("/{id}")
    public String dealership(Model model, @PathVariable Long id) {
        model.addAttribute("dealership", repoDealerships.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        return "dealership";
    }

    @GetMapping("/add")
    public String dealership_add(Model model) {
        AddAttributes(model);
        return "dealership_add";
    }

    @PostMapping("/add")
    public String dealership_add(Model model, @RequestParam String name, @RequestParam MultipartFile poster,  @RequestParam String address,@RequestParam String description) {
        try {
            String result_poster = "";
            String uuidFile = UUID.randomUUID().toString();
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
            }
            Users user = getUser();
            user.addDealership(new Dealerships(name, address, description, result_poster));
            repoUsers.save(user);
        } catch (IOException e) {
            AddAttributes(model);
            model.addAttribute("message", "Некорректные данные!");
            return "dealership_add";
        }
        return "redirect:/dealerships";
    }

    @GetMapping("/edit/{id}")
    public String dealership_edit(@PathVariable Long id, Model model) {
        if (!repoDealerships.existsById(id)) return "redirect:/dealerships";
        AddAttributesDealershipEdit(model, id);
        return "dealership_edit";
    }

    @PostMapping("/edit/{id}")
    public String dealership_edit(Model model, @PathVariable Long id, @RequestParam String name, @RequestParam String address, @RequestParam MultipartFile poster,  @RequestParam String description) {
        try {
            Dealerships dealerships = repoDealerships.getReferenceById(id);

            dealerships.setName(name);
            dealerships.setAddress(address);
            dealerships.setDescription(description);

            String uuidFile = UUID.randomUUID().toString();
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
                dealerships.setPoster(result_poster);
            }

            repoDealerships.save(dealerships);
        } catch (Exception e) {
            AddAttributesDealershipEdit(model, id);
            model.addAttribute("message", "Некорректные данные!");
            return "dealership_edit";
        }
        return "redirect:/dealerships";
    }

    @GetMapping("/delete/{id}")
    public String dealership_delete(@PathVariable Long id) {
        repoDealerships.deleteById(id);
        return "redirect:/dealerships";
    }
}
