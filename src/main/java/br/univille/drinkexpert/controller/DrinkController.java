package br.univille.drinkexpert.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.univille.drinkexpert.entity.Drink;
import br.univille.drinkexpert.service.DrinkService;
import org.springframework.web.bind.annotation.RequestParam;



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

        var drink = new Drink();

        ModelAndView modelAndView = new ModelAndView("searchDrink/form");
        modelAndView.addObject("listBaseDrink", listBaseDrink);
        modelAndView.addObject("listIngredientsDrink", listIngredientsDrink);
        modelAndView.addObject("listCaracteristicsDrink", listCaracteristicsDrink);
        modelAndView.addObject("drink", drink);

        return modelAndView;
    }

    @PostMapping("/form")
    public ModelAndView postSearchDrink(Drink drink, RedirectAttributes redirectAttributes){
        ArrayList<Drink> result = service.getResultDrinks(drink);
        redirectAttributes.addFlashAttribute("drinks", result);
        return new ModelAndView("redirect:/searchDrink/result","drinks",result);
    }

    @GetMapping("/result")
    public ModelAndView result(@ModelAttribute("drinks") ArrayList<Drink> drinks) {
        return new ModelAndView("searchDrink/result", "drinks", drinks);
    }
    
}
