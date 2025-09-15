package com.api.recetasapi.repository;

import com.api.recetasapi.DTO.IngredienteEnLocalizacionDTO;
import com.api.recetasapi.DTO.IngredienteLocalizadoDTO;
import com.api.recetasapi.entities.IngredienteLocalizadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredienteLocalizadoRepository extends JpaRepository<IngredienteLocalizadoEntity, Integer> {

    @Query(value = """
    SELECT new tu.paquete.dto.LocalizacionConIngredienteDTO(
      lc.id,
      lc.direccion,
      lc.latitud,
      lc.longitud,
      i.nombre,
      il.confiabilidad
    )
    FROM IngredienteLocalizado il
    JOIN il.localizaciones lc
    JOIN il.ingredientes i
    WHERE idingrediente = :idingrediente
      AND (
        6371 * acos(
          cos(radians(:lat)) *
          cos(radians(lc.latitud)) *
          cos(radians(lc.longitud) - radians(:lng)) +
          sin(radians(:lat)) *
          sin(radians(lc.latitud))
        )
      ) <= :radioKm
    ORDER BY il.confiabilidad DESC
""")
    List<IngredienteEnLocalizacionDTO> buscarLocalizacionesConIngredienteYConfiabilidad(int idingrediente, double lat, double lng, double radioKm);

    @Query("FROM IngredienteLocalizadoEntity il WHERE il.localizaciones.id = :idlocalizacion AND il.ingredientes.id = :idingrediente")
    IngredienteLocalizadoEntity findByBoth(int idingrediente, int idlocalizacion);

}
