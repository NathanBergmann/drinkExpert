package br.univille.drinkexpert.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Drink {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String baseDrink;
    private String modoPreparo;
    private String caracteristica;
    private ArrayList<Ingrediente> listaIngredientes = new ArrayList<Ingrediente>();

    private ArrayList<Drink> drinks = new ArrayList<Drink>();

    public Drink(){
        return;
    }

    public Drink(String nome, String baseDrink, String modoPreparo, String caracteristica, ArrayList<Ingrediente> listaIngredientes){
        setNome(nome);
        setBaseDrink(baseDrink);
        setModoPreparo(modoPreparo);
        setCaracteristica(caracteristica);
        setListIngredientes(listaIngredientes);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBaseDrink() {
        return baseDrink;
    }

    public void setBaseDrink(String baseDrink) {
        this.baseDrink = baseDrink;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public void addDrink(Drink drink) {
        this.drinks.add(drink);
    }

    public void setListIngredientes(ArrayList<Ingrediente> lista){
        this.listaIngredientes = lista;
    }

    public ArrayList<Ingrediente> getListIngredientes(){
        return this.listaIngredientes;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "drink_id")
    private List<Ingrediente> ingredientes;

}
