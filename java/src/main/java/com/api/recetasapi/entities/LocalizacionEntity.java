package com.api.recetasapi.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "localizaciones", schema = "cookbook_db", catalog = "")
public class LocalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "latitud")
    private double latitud;
    @Basic
    @Column(name = "longitud")
    private double longitud;
    @Basic
    @Column(name = "direccion")
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuariosEntity usuario;

    @OneToMany(mappedBy = "localizacion", cascade = CascadeType.ALL)
    private List<IngredienteLocalizadoEntity> ingredientes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public UsuariosEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosEntity usuario) {
        this.usuario = usuario;
    }

    public List<IngredienteLocalizadoEntity> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteLocalizadoEntity> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
