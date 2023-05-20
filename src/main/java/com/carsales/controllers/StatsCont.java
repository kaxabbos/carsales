package com.carsales.controllers;

import com.carsales.controllers.Main.Attributes;
import com.carsales.models.Dealerships;
import com.carsales.models.Cars;
import com.carsales.models.enums.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class StatsCont extends Attributes {

    @GetMapping("/stats")
    public String sales(Model model) {
        List<Dealerships> dealershipsList = getUser().getDealerships();
        List<Cars> cars = new ArrayList<>();
        for (Dealerships d : dealershipsList) {
            cars.addAll(d.getCars());
        }

        int[] brand = new int[Brand.values().length];
        List<Brand> brandList = Arrays.stream(Brand.values()).toList();
        int income = 0;
        for (Cars car : cars) {
            income += car.getIncome().getIncome();
            int index = 0;
            for (Brand i : brandList) {
                if (i == car.getBrand()) {
                    brand[index] += car.getIncome().getIncome();
                }
                index++;
            }
        }


        model.addAttribute("income", income);
        model.addAttribute("brand", brand);
        model.addAttribute("cars", cars);
        model.addAttribute("role", getRole());

        cars.sort(Comparator.comparing(Cars::getCount));
        Collections.reverse(cars);

        String[] topName = new String[5];
        int[] topNum = new int[5];

        for (int i = 0; i < cars.size(); i++) {
            if (i == 5) break;
            topName[i] = cars.get(i).getName();
            topNum[i] = cars.get(i).getIncome().getIncome();
        }
        model.addAttribute("topName", topName);
        model.addAttribute("topNum", topNum);

        return "stats";
    }
}
