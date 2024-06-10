package br.univille.drinkexpert.service;

import br.univille.drinkexpert.entity.Drink;
import br.univille.drinkexpert.entity.Ingrediente;
import br.univille.drinkexpert.repository.DrinkRepository;
import jakarta.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
@Service
public class DrinkService {

    @Autowired
    private DrinkRepository drinkRepository;

    @PostConstruct
    public void init() {
        loadDrinksFromJson();
    }

    public void loadDrinksFromJson() {
        Drink drinkList = new Drink();
         File file = new File("src/main/java/br/univille/drinkexpert/repository/database.json");
        try (JsonReader reader = Json.createReader(new FileReader(file.getAbsolutePath()))){
            // Lê o JSON em um objeto JsonObject
            JsonArray drinks = reader.readArray();

            // Percorre cada objeto no array JSON
            for (JsonObject drink : drinks.getValuesAs(JsonObject.class)) {
                // Obtém o número e o naipe da carta
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
                drinkList.addDrink(newDrink);
            }
            System.out.println("lista" + drinkList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       
       
       //FEITO PELO GPT
        // ObjectMapper objectMapper = new ObjectMapper();
        // TypeReference<List<Drink>> typeReference = new TypeReference<List<Drink>>() {};
        // InputStream inputStream = TypeReference.class.getResourceAsStream("src/main/java/br/univille/drinkexpert/repository/database.json");

        // try {
        //     List<Drink> drinks = objectMapper.readValue(inputStream, typeReference); //erro json
        //     drinkRepository.saveAll(drinks);
        //     System.out.println("Drinks loaded and saved to the database!");
        // } catch (IOException e) {
        //     System.out.println("Unable to load drinks: " + e.getMessage());
        // }
    }
}

