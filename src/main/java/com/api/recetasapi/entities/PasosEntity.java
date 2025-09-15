package com.api.recetasapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pasos", schema = "cookbook_db", catalog = "")
public class PasosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idpaso")
    private int idPaso;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="idreceta")
    private RecetasEntity receta;

    @Basic
    @Column(name = "nropaso")
    private Integer nroPaso;

    @Basic
    @Column(name = "texto")
    private String texto;

    @Basic
    @Column(name = "titulo")
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @OneToMany(mappedBy = "paso",cascade = CascadeType.ALL, orphanRemoval=true)
    private List<MultimediaEntity> multimedia;

    public int getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(int idPaso) {
        this.idPaso = idPaso;
    }

    public Integer getNroPaso() {
        return nroPaso;
    }

    public void setNroPaso(Integer nroPaso) {
        this.nroPaso = nroPaso;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public RecetasEntity getReceta() {
        return receta;
    }

    public void setReceta(RecetasEntity receta) {
        this.receta = receta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasosEntity that = (PasosEntity) o;
        return idPaso == that.idPaso && Objects.equals(receta, that.receta) && Objects.equals(nroPaso, that.nroPaso) && Objects.equals(texto, that.texto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPaso, receta, nroPaso, texto);
    }
}
