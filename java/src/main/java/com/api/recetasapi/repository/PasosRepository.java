package com.api.recetasapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.recetasapi.entities.PasosEntity;
import com.api.recetasapi.entities.RecetasEntity;
import com.api.recetasapi.entities.UnidadesEntity;

public interface PasosRepository extends JpaRepository<PasosEntity, Integer> {
	
	@Query("From PasosEntity p where p.receta = :receta")
    List<PasosEntity> findPasosByReceta(RecetasEntity receta);
	
	@Query("From PasosEntity p where p.receta = :receta and p.nroPaso = :nro")
    List<PasosEntity> findPasoByRecetaNumero(RecetasEntity receta, Integer nro);

}
