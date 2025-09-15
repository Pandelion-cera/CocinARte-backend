package com.api.recetasapi.entities;

import com.api.recetasapi.model.Receta;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "recetas", schema = "cookbook_db", catalog = "")
public class RecetasEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idreceta")
    private int idReceta;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name="idusuario")
    private UsuariosEntity usuarioCreador;

    @Basic
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    @Basic
    @Column(name = "foto")
    private String foto;

    @Basic
    @Column(name = "porciones")
    private Integer porciones;

    @Basic
    @Column(name = "cantidadpersonas")
    private Integer cantidadPersonas;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name="idtipo")
    private TiposEntity tipoReceta;

    @Basic
    @Column(name = "verificacion")
    private Byte verificacion;


    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }
    
    public void setFechaCargaCurrent() {
        this.fechaCarga = new Date();
    }

    @Basic
    @Column(name = "fecha_carga")
    private Date fechaCarga;

    @ManyToMany(mappedBy = "misRecetasPorHacer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsuariosEntity> usuariosQueGuardaronEstaReceta;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name="utilizados", joinColumns = @JoinColumn(name="idreceta"), inverseJoinColumns = @JoinColumn(name="idingrediente"))
    private List<IngredientesEntity> ingredientesUtilizados;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<UtilizadosEntity> ingUtilizados;

    @OneToMany(mappedBy = "receta",cascade = CascadeType.ALL, orphanRemoval=true)
    private List<PasosEntity> pasos;

    @OneToMany(mappedBy = "receta",cascade = CascadeType.ALL, orphanRemoval=true)
    private List<CalificacionesEntity> calificaciones;

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getPorciones() {
        return porciones;
    }

    public void setPorciones(Integer porciones) { this.porciones = porciones; }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Byte getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(Byte verificacion) {
        this.verificacion = verificacion;
    }

    public UsuariosEntity getUsuarioCreador() { return usuarioCreador; }

    public void setUsuarioCreador(UsuariosEntity usuarioCreador) {
        this.usuarioCreador = usuarioCreador; }

    public TiposEntity getTipoReceta() { return tipoReceta; }

    public void setTipoReceta(TiposEntity tipoReceta) { this.tipoReceta = tipoReceta; }

    public List<UsuariosEntity> getUsuariosQueGuardaronEstaReceta() {
        return usuariosQueGuardaronEstaReceta;
    }

    public void setUsuariosQueGuardaronEstaReceta(List<UsuariosEntity> usuariosQueGuardaronEstaReceta) {
        this.usuariosQueGuardaronEstaReceta = usuariosQueGuardaronEstaReceta;
    }

    public List<IngredientesEntity> getIngredientesUtilizados() {
        return ingredientesUtilizados;
    }

    public void setIngredientesUtilizados(List<IngredientesEntity> ingredientesUtilizados) {
        this.ingredientesUtilizados = ingredientesUtilizados;
    }

    public List<PasosEntity> getPasos() {
        return pasos;
    }

    public void setPasos(List<PasosEntity> pasos) {
        this.pasos = pasos;
    }

    public List<CalificacionesEntity> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionesEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<UtilizadosEntity> getIngUtilizados() {
        return ingUtilizados;
    }

    public void setIngUtilizados(List<UtilizadosEntity> ingUtilizados) {
        this.ingUtilizados = ingUtilizados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecetasEntity that = (RecetasEntity) o;
        return idReceta == that.idReceta && Objects.equals(usuarioCreador, that.usuarioCreador) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(foto, that.foto) && Objects.equals(porciones, that.porciones) && Objects.equals(cantidadPersonas, that.cantidadPersonas) && Objects.equals(tipoReceta, that.tipoReceta) && Objects.equals(verificacion, that.verificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReceta, usuarioCreador, nombre, descripcion, foto, porciones, cantidadPersonas, tipoReceta, verificacion);
    }

}
