package com.api.recetasapi.DTO;

import lombok.Data;

@Data
public class LocalizacionDTO {

    private int id;
    private String nombre;
    private double latitud;
    private double longitud;
    private String direccion;
    private int confiabilidad;
}
