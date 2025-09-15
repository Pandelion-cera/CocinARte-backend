package com.api.recetasapi.repository;

import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.UnidadesEntity;
import com.api.recetasapi.entities.UtilizadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface UnidadesRepository extends JpaRepository<UnidadesEntity, Integer> {
	
	@Query("FROM UnidadesEntity t WHERE t.descripcion = :nombre")
    UnidadesEntity findByName(String nombre);

}
