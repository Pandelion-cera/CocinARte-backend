package com.api.recetasapi.DTO;

import lombok.Data;

@Data
public class IngredienteEnLocalizacionDTO {

    public IngredienteEnLocalizacionDTO(Long localizacionId, String direccion, double latitud, double longitud, String ingrediente, int confiabilidad) {
        this.localizacionId = localizacionId;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ingrediente = ingrediente;
        this.confiabilidad = confiabilidad;
    }

    private Long localizacionId;
    private String direccion;
    private double latitud;
    private double longitud;
    private String ingrediente;
    private int confiabilidad;

}
