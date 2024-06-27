package br.univille.drinkexpert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import br.univille.drinkexpert.service.DrinkService;


@Controller
@RequestMapping("/searchDrink")
public class DrinkController {
    
    @Autowired
    private DrinkService service;

    @GetMapping()
    public ModelAndView searchDrink(){
        List<String> listBaseDrink = service.getBaseDrinkList();
        List<String> listIngredientsDrink = service.getDrinkIngredients();
        List<String> listCaracteristicsDrink = service.getDrinkCaracteristics();

        ModelAndView modelAndView = new ModelAndView("searchDrink/form");
        modelAndView.addObject("listBaseDrink", listBaseDrink);
        modelAndView.addObject("listIngredientsDrink", listIngredientsDrink);
        modelAndView.addObject("listCaracteristicsDrink", listCaracteristicsDrink);
        return modelAndView;
    }

}
