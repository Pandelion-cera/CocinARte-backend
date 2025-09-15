package com.api.recetasapi.entities;

import com.api.recetasapi.model.Receta;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "utilizados", schema = "cookbook_db", catalog = "")
public class UtilizadosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idutilizado")
    private int idUtilizado;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="idreceta")
    private RecetasEntity receta;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name="idingrediente")
    private IngredientesEntity ingrediente;

    @Basic
    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name="idunidad")
    private UnidadesEntity unidad;

    @Basic
    @Column(name = "observaciones")
    private String observaciones;

    public UtilizadosEntity() {
    }

    public UtilizadosEntity(IngredientesEntity ingrediente, int cantidad, UnidadesEntity unidad, RecetasEntity receta) {
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.receta = receta;
    }
    public int getIdUtilizado() {
        return idUtilizado;
    }

    public void setIdUtilizado(int idUtilizado) {
        this.idUtilizado = idUtilizado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public RecetasEntity getReceta() {
        return receta;
    }

    public void setReceta(RecetasEntity receta) {
        this.receta = receta;
    }

    public IngredientesEntity getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredientesEntity ingrediente) {
        this.ingrediente = ingrediente;
    }

    public UnidadesEntity getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadesEntity unidad) {
        this.unidad = unidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilizadosEntity that = (UtilizadosEntity) o;
        return idUtilizado == that.idUtilizado && Objects.equals(receta, that.receta) && Objects.equals(ingrediente, that.ingrediente) && Objects.equals(cantidad, that.cantidad) && Objects.equals(unidad, that.unidad) && Objects.equals(observaciones, that.observaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilizado, receta, ingrediente, cantidad, unidad, observaciones);
    }
}
