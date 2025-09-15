package com.api.recetasapi.repository;

import com.api.recetasapi.DTO.IngredienteEnLocalizacionDTO;
import com.api.recetasapi.DTO.IngredienteLocalizadoDTO;
import com.api.recetasapi.entities.IngredienteLocalizadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteLocalizadoRepository extends JpaRepository<IngredienteLocalizadoEntity, Integer> {

    @Query("SELECT new com.api.recetasapi.DTO.IngredienteEnLocalizacionDTO( " +
            "lc.id, lc.direccion, lc.latitud, lc.longitud, i.nombre, il.confiabilidad) " +
            "FROM IngredienteLocalizadoEntity il " +
            "JOIN il.localizacion lc " +
            "JOIN il.ingrediente i " +
            "WHERE i.id = :idingrediente " +
            "AND (6371 * acos(cos(radians(:lat)) * cos(radians(lc.latitud)) * " +
            "cos(radians(lc.longitud) - radians(:lng)) + sin(radians(:lat)) * sin(radians(lc.latitud)))) <= :radioKm " +
            "ORDER BY il.confiabilidad DESC")
    List<IngredienteEnLocalizacionDTO> buscarLocalizacionesConIngredienteYConfiabilidad(
            @Param("idingrediente") int idIngrediente,
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radioKm") double radioKm
    );

    @Query("FROM IngredienteLocalizadoEntity il WHERE il.localizacion.id = :idlocalizacion AND il.ingrediente.id = :idingrediente")
    IngredienteLocalizadoEntity findByBoth(int idingrediente, int idlocalizacion);

}
