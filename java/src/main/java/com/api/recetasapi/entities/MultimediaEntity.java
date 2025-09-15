package com.api.recetasapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "multimedia", schema = "cookbook_db", catalog = "")
public class MultimediaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idcontenido")
    private int idContenido;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="idpaso")
    private PasosEntity paso;

    @Basic
    @Column(name = "tipo_contenido")
    private String tipoContenido;
    @Basic
    @Column(name = "extension")
    private String extension;
    @Basic
    @Column(name = "urlcontenido")
    private String urlContenido;

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getUrlContenido() {
        return urlContenido;
    }

    public void setUrlContenido(String urlContenido) {
        this.urlContenido = urlContenido;
    }

    public PasosEntity getPaso() {
        return paso;
    }

    public void setPaso(PasosEntity paso) {
        this.paso = paso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultimediaEntity that = (MultimediaEntity) o;
        return idContenido == that.idContenido && paso == that.paso && Objects.equals(tipoContenido, that.tipoContenido) && Objects.equals(extension, that.extension) && Objects.equals(urlContenido, that.urlContenido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContenido, paso, tipoContenido, extension, urlContenido);
    }
}
