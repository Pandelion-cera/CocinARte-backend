package com.api.recetasapi.entities;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "ingredientelocalizado", schema = "cookbook_db", catalog = "")
public class IngredienteLocalizadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "localizacion_id")
    private LocalizacionEntity localizacion;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id")
    private IngredientesEntity ingrediente;

    @Basic
    @Column(name = "confiabilidad")
    private int confiabilidad;

    public IngredienteLocalizadoEntity(LocalizacionEntity localizacion, IngredientesEntity ingrediente, int confiabilidad) {
        this.localizacion = localizacion;
        this.ingrediente = ingrediente;
        this.confiabilidad = confiabilidad;
    }

    public IngredienteLocalizadoEntity(Optional<LocalizacionEntity> byId, Optional<IngredientesEntity> byId1, int confiabilidad) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalizacionEntity getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(LocalizacionEntity localizacion) {
        this.localizacion = localizacion;
    }

    public IngredientesEntity getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredientesEntity ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getConfiabilidad() {
        return confiabilidad;
    }

    public void setConfiabilidad(int confiabilidad) {
        this.confiabilidad = confiabilidad;
    }

}
