package com.api.recetasapi.repository;

import com.api.recetasapi.entities.RecetasEntity;
import com.api.recetasapi.entities.TiposEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TiposRepository extends JpaRepository<TiposEntity, Integer> {
	
    @Query("FROM TiposEntity t WHERE t.descripcion = :textoBusqueda")
    TiposEntity findByText(String textoBusqueda);

}
