package com.api.recetasapi.repository;

import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.LocalizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacionRepository  extends JpaRepository<LocalizacionEntity, Integer> {

    @Query(value = """
  SELECT DISTINCT lc.*
  FROM localizaciones lc
  JOIN localizacion_ingredientes li ON lc.id = li.localizacion_id
  JOIN ingredientes i ON i.id = li.ingrediente_id
  WHERE i.idingrediente = :idingrediente
    AND (
      6371 * acos(
        cos(radians(:lat)) *
        cos(radians(lc.latitud)) *
        cos(radians(lc.longitud) - radians(:lng)) +
        sin(radians(:lat)) *
        sin(radians(lc.latitud))
      )
    ) <= :radio
  """, nativeQuery = true)
    LocalizacionEntity findByLocalizacion(int idingrediente, double lat, double lng, double radio);

}
