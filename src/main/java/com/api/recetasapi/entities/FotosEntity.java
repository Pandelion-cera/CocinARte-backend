package com.api.recetasapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fotos", schema = "cookbook_db", catalog = "")
public class FotosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idfoto")
    private int idfoto;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="idreceta")
    private RecetasEntity receta;

    @Basic
    @Column(name = "urlfoto")
    private String urlFoto;
    @Basic
    @Column(name = "extension")
    private String extension;

    public int getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(int idfoto) {
        this.idfoto = idfoto;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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
        FotosEntity that = (FotosEntity) o;
        return idfoto == that.idfoto && receta == that.receta && Objects.equals(urlFoto, that.urlFoto) && Objects.equals(extension, that.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idfoto, receta, urlFoto, extension);
    }
}
