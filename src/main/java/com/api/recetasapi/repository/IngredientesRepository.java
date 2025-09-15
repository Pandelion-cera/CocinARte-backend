package com.api.recetasapi.repository;

import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.TiposEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientesRepository extends JpaRepository<IngredientesEntity, Integer> {
    @Query("FROM IngredientesEntity t WHERE t.nombre LIKE %:ingrediente%")
    List<IngredientesEntity> findByText(String ingrediente);

    @Query("FROM IngredientesEntity t WHERE t.nombre = :nombre")
    IngredientesEntity findByName(String nombre);
}
