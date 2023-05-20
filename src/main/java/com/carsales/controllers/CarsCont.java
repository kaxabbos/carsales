package com.carsales.controllers;

import com.carsales.controllers.Main.Attributes;
import com.carsales.models.*;
import com.carsales.models.enums.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/cars")
public class CarsCont extends Attributes {

    @GetMapping("/{id}")
    public String car(@PathVariable Long id, Model model) {
        if (!repoCars.existsById(id)) return "redirect:/catalog";
        model.addAttribute("s", repoCars.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        return "car";
    }

    @PostMapping("/comment/add/{id}")
    public String comment_add(@PathVariable Long id, @RequestParam String date, @RequestParam String comment) {
        Cars car = repoCars.getReferenceById(id);
        car.addComment(new Comments(getUser().getUsername(), date, comment));
        repoCars.save(car);
        return "redirect:/cars/{id}";
    }

    @GetMapping("/buy/{id}")
    public String buy(@PathVariable Long id) {
        int count = 1;
        Cars car = repoCars.getReferenceById(id);

        Users user = getUser();
        user.addCart(new Carts(count, car.getIncome().getPrice(), (car.getIncome().getPrice() * count), car));
        repoUsers.save(user);

        car.setCount(car.getCount() - count);

        car.getIncome().setCount(car.getIncome().getCount() + count);
        car.getIncome().setIncome(car.getIncome().getIncome() + (count * car.getIncome().getPrice()));

        repoCars.save(car);

        return "redirect:/cars/{id}";
    }

    @GetMapping("/add/{id}")
    public String car_add(@PathVariable Long id, Model model) {
        model.addAttribute("dealership", repoDealerships.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("brands", Brand.values());
        return "car_add";
    }

    @PostMapping("/add")
    public String car_add(Model model, @RequestParam long dealershipId, @RequestParam String name, @RequestParam MultipartFile poster, @RequestParam MultipartFile[] screenshots, @RequestParam String origin, @RequestParam String date, @RequestParam int year, @RequestParam int price, @RequestParam int count, @RequestParam Brand brand, @RequestParam String description) {
        try {
            String uuidFile = UUID.randomUUID().toString();
            String result_poster = "";
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
            }

            String[] result_screenshots = new String[0];
            if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                String result_screenshot;
                result_screenshots = new String[screenshots.length];
                for (int i = 0; i < result_screenshots.length; i++) {
                    result_screenshot = uuidFile + "_" + screenshots[i].getOriginalFilename();
                    screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                    result_screenshots[i] = result_screenshot;
                }
            }

            Dealerships dealership = repoDealerships.getReferenceById(dealershipId);
            dealership.addCar(new Cars(name, origin, description, date, result_poster, result_screenshots, year, price, count, brand));
            repoDealerships.save(dealership);

        } catch (Exception e) {
            model.addAttribute("author", repoDealerships.getReferenceById(dealershipId));
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Некорректные данные!");
            model.addAttribute("brands", Brand.values());
            return "car_add";
        }
        return "redirect:/catalog/all";
    }

    @GetMapping("/edit/{id}")
    public String car_edit(@PathVariable(value = "id") Long id, Model model) {
        if (!repoCars.existsById(id)) return "redirect:/catalog";
        model.addAttribute("s", repoCars.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("brands", Brand.values());
        return "car_edit";
    }

    @PostMapping("/edit/{id}")
    public String car_edit(@PathVariable Long id, Model model, @RequestParam String name, @RequestParam MultipartFile poster, @RequestParam MultipartFile[] screenshots, @RequestParam String origin, @RequestParam String date, @RequestParam int year, @RequestParam int price, @RequestParam int count, @RequestParam Brand brand, @RequestParam String description) {
        try {
            Cars car = repoCars.getReferenceById(id);

            car.setDescription(description);
            car.setName(name);
            car.setOrigin(origin);
            car.setCount(count);
            car.setYear(year);
            car.setDate(date);
            car.getIncome().setPrice(price);
            car.setBrand(brand);

            String uuidFile = UUID.randomUUID().toString();
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
                car.setPoster(result_poster);
            }

            if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                String result_screenshot;
                String[] result_screenshots = new String[screenshots.length];
                for (int i = 0; i < result_screenshots.length; i++) {
                    result_screenshot = uuidFile + "_" + screenshots[i].getOriginalFilename();
                    screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                    result_screenshots[i] = result_screenshot;
                }
                car.setScreenshots(result_screenshots);
            }
            repoCars.save(car);
        } catch (Exception e) {
            model.addAttribute("car", repoCars.getReferenceById(id));
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Некорректные данные!");
            model.addAttribute("brands", Brand.values());
            return "car_edit";
        }
        return "redirect:/cars/{id}/";
    }

    @GetMapping("/delete/{id}")
    public String car_delete(@PathVariable Long id) {
        repoCars.deleteById(id);
        return "redirect:/catalog/all";
    }
}
