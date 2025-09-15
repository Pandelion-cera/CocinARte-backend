package com.api.recetasapi.repository;

import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.RecetasEntity;
import com.api.recetasapi.entities.TiposEntity;
import com.api.recetasapi.entities.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetasRepository extends JpaRepository<RecetasEntity, Integer> {
    @Query("FROM RecetasEntity u WHERE u.idReceta = :idReceta")
    RecetasEntity getById(Integer idReceta);

    @Query("FROM RecetasEntity u WHERE u.verificacion = 1 ORDER BY u.nombre ASC")
    List<RecetasEntity> findVerificadas();

    @Query("FROM RecetasEntity u WHERE u.verificacion = 1 ORDER BY u.fechaCarga DESC")
    List<RecetasEntity> findVerificadasOrderByFechaDesc();

    @Query("FROM RecetasEntity u WHERE u.verificacion = 1 ORDER BY u.fechaCarga ASC")
    List<RecetasEntity> findVerificadasOrderByFechaAsc();

    @Query("FROM RecetasEntity u WHERE u.nombre LIKE %:textoBusqueda%")
    List<RecetasEntity> findByText(String textoBusqueda);
    
    @Query("FROM RecetasEntity u WHERE u.nombre = :nombre")
    List<RecetasEntity> findByName(String nombre);

    @Query("FROM RecetasEntity u WHERE u.tipoReceta = :tipo")
    List<RecetasEntity> findByTipo(TiposEntity tipo);
    
    @Query("SELECT DISTINCT u.usuarioCreador FROM RecetasEntity u")
    List<UsuariosEntity> findCreators();

    @Query("From RecetasEntity r where :ingrediente member r.ingredientesUtilizados")
    List<RecetasEntity> findByIngrediente(IngredientesEntity ingrediente);

    @Query("From RecetasEntity r where :ingrediente not member r.ingredientesUtilizados")
    List<RecetasEntity> findSinIngrediente(IngredientesEntity ingrediente);

    @Query("FROM RecetasEntity u WHERE u.verificacion = 1 ORDER BY u.usuarioCreador.idUsuario, u.nombre ASC")
    List<RecetasEntity> findVerificadasOrderByUsuario();
}
