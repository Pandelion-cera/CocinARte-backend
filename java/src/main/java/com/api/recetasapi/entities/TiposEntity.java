package com.api.recetasapi.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tipos", schema = "cookbook_db", catalog = "")
public class TiposEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipo")
    private int idTipo;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
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
        TiposEntity that = (TiposEntity) o;
        return idTipo == that.idTipo && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipo, descripcion);
    }
}
