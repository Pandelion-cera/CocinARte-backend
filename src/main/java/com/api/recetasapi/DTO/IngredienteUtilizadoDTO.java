package com.api.recetasapi.DTO;

import lombok.Data;

@Data
public class IngredienteUtilizadoDTO {
    private int idIngredienteUtilizado;
    private int cantidad;
    private String idIngrediente;
    private String idUnidad;
    private String idReceta;
    private String observaciones;
}
