package com.api.recetasapi.repository;

import com.api.recetasapi.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CalificacionesRepository extends JpaRepository<CalificacionesEntity, Integer> {
    @Query("FROM CalificacionesEntity c WHERE c.receta = :receta and c.usuario = :usuario")
    CalificacionesEntity findByUsuarioReceta(RecetasEntity receta, UsuariosEntity usuario);
}
