package com.api.recetasapi.DTO;

import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.PasosEntity;
import com.api.recetasapi.entities.TiposEntity;
import com.api.recetasapi.entities.UsuariosEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class RecetaDTO {

    private Integer idReceta;
    private UsuarioDTO usuarioCreador;
    private String nombre;
    private String descripcion;
    private String foto;
    private Integer porciones;
    private Integer cantidadPersonas;
    private TiposDTO tipoReceta;
    private Byte verificacion;
    private List<IngredienteUtilizadoDTO> ingredientesUtilizados;
    private List<PasoDTO> pasos;
    
}
