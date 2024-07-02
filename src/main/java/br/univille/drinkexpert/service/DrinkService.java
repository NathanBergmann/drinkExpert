package br.univille.drinkexpert.service;

import br.univille.drinkexpert.entity.Drink;
import br.univille.drinkexpert.entity.Ingrediente;
import jakarta.annotation.PostConstruct;

import org.hibernate.dialect.function.array.ArraySliceUnnestFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.stream.Collectors;

@Service
public class DrinkService {

    private List<Drink> drinkList = new ArrayList<Drink>();


    @PostConstruct
    public void init() {
        loadDrinksFromJson();
    }

    public void loadDrinksFromJson() {

         File file = new File("src/main/java/br/univille/drinkexpert/repository/database.json");
        try (JsonReader reader = Json.createReader(new FileReader(file.getAbsolutePath()))){
            // Lê o JSON em um objeto JsonObject
            JsonArray drinks = reader.readArray();
            // Percorre cada objeto no array JSON
            for (JsonObject drink : drinks.getValuesAs(JsonObject.class)) {
                String nome = drink.getString("name");  
                String baseDrink = drink.getString("baseDrink");
                String modoPreparo = drink.getString("modoPreparo");
                String caracteristica = drink.getString("caracteristica");
                JsonArray ingredientes = drink.getJsonArray("ingredientes");
                ArrayList<Ingrediente> listaIngredientes = new ArrayList<Ingrediente>();
                for (int j = 0; j < ingredientes.size(); j++) {
                    JsonObject ingrediente = ingredientes.getJsonObject(j);
                    String nomeIngred = ingrediente.getString("nome");
                    int quantidade = ingrediente.getInt("quantidade");
                    String unidade = ingrediente.getString("unidade");
                    Ingrediente ingredienteIndividual = new Ingrediente(nomeIngred, quantidade, unidade);
                    listaIngredientes.add(ingredienteIndividual);
                }
    
                Drink newDrink = new Drink(nome, baseDrink, modoPreparo, caracteristica, listaIngredientes);
                drinkList.add(newDrink);
            }
             System.out.println("lista" + getBaseDrinkList());
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public List<String> getBaseDrinkList() {
        List<String> baseDrinkList = this.drinkList.stream()
            .map(Drink::getBaseDrink) // Mapeia cada Drink para seu baseDrink
            .collect(Collectors.toSet()) // Coleta para um Set para remover duplicatas
            .stream()
            .collect(Collectors.toList());
        return baseDrinkList;
    }

    public List<String> getDrinkIngredients() {
        Set<String> listaIngredientesHash = new HashSet<>();
        for (Drink drink : drinkList) {
            for (Ingrediente ingrediente : drink.getListaIngredientes()){
                listaIngredientesHash.add(ingrediente.getNome()); 
            }
        }
        List<String> listaIngrientes = new ArrayList<>(listaIngredientesHash);
        return listaIngrientes;
    }

    public List<String> getDrinkCaracteristics() {
        Set<String> listaCaracteristicsHash = new HashSet<>();
        for (Drink drink : drinkList) {
                listaCaracteristicsHash.add(drink.getCaracteristica()); 
            }
        List<String> listaCaracteristicas = new ArrayList<>(listaCaracteristicsHash);
        return listaCaracteristicas;
    }

    public ArrayList<Drink> getResultDrinks(Drink drink){
        
        String ingrediente = drink.getIngredientes().stream()
        .map(Ingrediente:: getNome)
        .collect(Collectors.joining(" "));
        String baseDrink = drink.getBaseDrink();
        String caracteristica = drink.getCaracteristica();
        drink.getIngredientes();

        List<Drink> resultList = drinkList.stream()
            .filter(filterDrink -> baseDrink.isEmpty() || filterDrink.getBaseDrink().equalsIgnoreCase(baseDrink))
            .filter(filterDrink -> ingrediente.isEmpty() || filterDrink.getIngredientes().stream().anyMatch(ing -> ing.getNome().equalsIgnoreCase(ingrediente)))
            .filter(filterDrink -> caracteristica.isEmpty() || filterDrink.getCaracteristica().equalsIgnoreCase(caracteristica))
            .collect(Collectors.toList());
        
        ArrayList<Drink> result = new ArrayList<>(resultList);

        return result;
    }
}

