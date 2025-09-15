package com.api.recetasapi.DTO;

import lombok.Data;

@Data
public class IngredienteEnLocalizacionDTO {

    private Long localizacionId;
    private String direccion;
    private double latitud;
    private double longitud;
    private String ingrediente;
    private int confiabilidad;

}
