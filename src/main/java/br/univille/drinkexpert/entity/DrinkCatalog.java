package br.univille.drinkexpert.entity;

import java.util.ArrayList;

public class DrinkCatalog {

    private ArrayList<Drink> drinks = new ArrayList<Drink>();

    public void addDrink(Drink drink) {
        this.drinks.add(drink);
    }
}
