package com.api.recetasapi.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "conversiones", schema = "cookbook_db", catalog = "")
public class ConversionesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idconversion")
    private int idConversion;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name="idunidadorigen")
    private UnidadesEntity unidadOrigen;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name="idunidaddestino")
    private UnidadesEntity unidadDestino;

    @Basic
    @Column(name = "factorconversiones")
    private Double factorConversiones;

    public int getIdConversion() {
        return idConversion;
    }

    public void setIdConversion(int idConversion) {
        this.idConversion = idConversion;
    }

    public Double getFactorConversiones() {
        return factorConversiones;
    }

    public void setFactorConversiones(Double factorConversiones) {
        this.factorConversiones = factorConversiones;
    }

    public UnidadesEntity getUnidadOrigen() {
        return unidadOrigen;
    }

    public void setUnidadOrigen(UnidadesEntity unidadOrigen) {
        this.unidadOrigen = unidadOrigen;
    }

    public UnidadesEntity getUnidadDestino() {
        return unidadDestino;
    }

    public void setUnidadDestino(UnidadesEntity unidadDestino) {
        this.unidadDestino = unidadDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversionesEntity that = (ConversionesEntity) o;
        return idConversion == that.idConversion && unidadOrigen == that.unidadOrigen && unidadDestino == that.unidadDestino && Objects.equals(factorConversiones, that.factorConversiones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConversion, unidadOrigen, unidadDestino, factorConversiones);
    }
}
