package com.api.recetasapi.repository;

import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.LocalizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacionRepository  extends JpaRepository<LocalizacionEntity, Integer> {


}
