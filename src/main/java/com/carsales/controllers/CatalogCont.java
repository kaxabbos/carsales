package com.carsales.controllers;

import com.carsales.controllers.Main.Attributes;
import com.carsales.models.Cars;
import com.carsales.models.enums.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CatalogCont extends Attributes {
    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("cars", repoCars.findAll());
        model.addAttribute("brands", Brand.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @GetMapping("/catalog/all")
    public String catalog_main(Model model) {
        model.addAttribute("cars", repoCars.findAll());
        model.addAttribute("brands", Brand.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @PostMapping("/catalog/search")
    public String search(@RequestParam Brand brand, @RequestParam String date_range, Model model) {
        String[] date = date_range.split(" ");

        int with = Integer.parseInt(date[0]);
        int before = Integer.parseInt(date[2]);

        List<Cars> temp = repoCars.findAllByBrand(brand);
        List<Cars> cars = temp.stream().filter(car -> (with <= car.getYear() && car.getYear() <= before)).toList();

        model.addAttribute("cars", cars);
        model.addAttribute("brands", Brand.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @GetMapping("/catalog/brand/{brand}")
    public String catalog_brand_search(@PathVariable(value = "brand") Brand brand, Model model) {
        model.addAttribute("cars", repoCars.findAllByBrand(brand));
        model.addAttribute("brands", Brand.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @GetMapping("/catalog/year/{year}")
    public String catalog_year_search(@PathVariable(value = "year") int year, Model model) {
        model.addAttribute("cars", repoCars.findAllByYear(year));
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @PostMapping("/catalog/search/name")
    public String catalogSearch(@RequestParam String search, Model model) {
        model.addAttribute("cars", repoCars.findAllByNameContaining(search));
        model.addAttribute("brands", Brand.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }
}
