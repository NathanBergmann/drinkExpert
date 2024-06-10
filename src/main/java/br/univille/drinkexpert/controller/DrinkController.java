package br.univille.drinkexpert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.univille.drinkexpert.entity.Drink;
import br.univille.drinkexpert.repository.DrinkRepository;


@Controller
@RequestMapping("/searchDrink")

public class DrinkController {
    
    @Autowired
    private DrinkRepository drinkRepository;

    @GetMapping("/")
    public String redirectToSearchForm(){
        return "redirect:/searchDrink/form";
    }

    @GetMapping("searchDrink/form")
    public ModelAndView searchDrink(){
        return new ModelAndView("searchDrink/form");
    }

    @GetMapping()
    public List<Drink> getAllDrinks() {
        return drinkRepository.findAll();
    }
}
