package com.api.recetasapi.entities;

import javax.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "calificaciones", schema = "cookbook_db", catalog = "")
public class CalificacionesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idcalificacion")
    private int idCalificacion;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name="idusuario")
    private UsuariosEntity usuario;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="idreceta")
    private RecetasEntity receta;

    @Basic
    @Column(name = "autenticidad")
    private Float autenticidad;

    @Basic
    @Column(name = "calificacion")
    private float calificacion;

    @Basic
    @Column(name = "comentarios")
    private String comentarios;

    public int getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(int idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public UsuariosEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosEntity usuario) {
        this.usuario = usuario;
    }

    public RecetasEntity getReceta() {
        return receta;
    }

    public void setReceta(RecetasEntity receta) {
        this.receta = receta;
    }

    public Float getAutenticidad() {return autenticidad;}

    public void setAutenticidad(Float autenticidad) {this.autenticidad = autenticidad;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalificacionesEntity that = (CalificacionesEntity) o;
        return idCalificacion == that.idCalificacion && Objects.equals(usuario, that.usuario) && Objects.equals(receta, that.receta) && Objects.equals(autenticidad, that.autenticidad) && Objects.equals(calificacion, that.calificacion) && Objects.equals(comentarios, that.comentarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCalificacion, usuario, receta, autenticidad, calificacion, comentarios);
    }
}
