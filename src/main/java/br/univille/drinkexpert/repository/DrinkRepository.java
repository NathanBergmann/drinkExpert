package br.univille.drinkexpert.repository;


import br.univille.drinkexpert.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
}
