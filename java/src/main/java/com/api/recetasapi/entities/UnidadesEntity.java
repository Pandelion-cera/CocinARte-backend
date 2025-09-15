package com.api.recetasapi.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "unidades", schema = "cookbook_db", catalog = "")
public class UnidadesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @Column(name = "idunidad")
    private int idUnidad;

    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadesEntity that = (UnidadesEntity) o;
        return idUnidad == that.idUnidad && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUnidad, descripcion);
    }
}
