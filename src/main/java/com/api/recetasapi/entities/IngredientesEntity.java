package com.api.recetasapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ingredientes", schema = "cookbook_db", catalog = "")
public class IngredientesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idingrediente")
    private int idIngrediente;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "ingrediente")
    private List<IngredienteLocalizadoEntity> localizaciones;

    @ManyToMany(mappedBy = "ingredientesUtilizados", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RecetasEntity> recetasDeEsteIngrediente;

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<IngredienteLocalizadoEntity> getLocalizaciones() {
        return localizaciones;
    }

    public void setLocalizaciones(List<IngredienteLocalizadoEntity> localizaciones) {
        this.localizaciones = localizaciones;
    }

    public List<RecetasEntity> getRecetasDeEsteIngrediente() {
        return recetasDeEsteIngrediente;
    }

    public void setRecetasDeEsteIngrediente(List<RecetasEntity> recetasDeEsteIngrediente) {
        this.recetasDeEsteIngrediente = recetasDeEsteIngrediente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientesEntity that = (IngredientesEntity) o;
        return idIngrediente == that.idIngrediente && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIngrediente, nombre);
    }
}
