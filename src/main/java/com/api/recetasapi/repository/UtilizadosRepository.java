package com.api.recetasapi.repository;

import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.RecetasEntity;
import com.api.recetasapi.entities.UsuariosEntity;
import com.api.recetasapi.entities.UtilizadosEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface UtilizadosRepository extends JpaRepository<UtilizadosEntity, Integer> {
	
	@Query("Select u.receta From UtilizadosEntity u where u.ingrediente = :ingrediente")
    List<RecetasEntity> findRecetasByIngrediente(IngredientesEntity ingrediente);
	
	@Query("Select u.ingrediente From UtilizadosEntity u where u.receta = :receta")
    List<IngredientesEntity> findIngredientesByReceta(RecetasEntity receta);
	
	@Query("From UtilizadosEntity u where u.receta = :receta and u.ingrediente = :ingrediente")
    UtilizadosEntity findByBoth(IngredientesEntity ingrediente, RecetasEntity receta);

}
