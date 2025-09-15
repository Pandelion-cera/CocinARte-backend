package com.api.recetasapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usuarios_recetas_mis_recetas", schema = "cookbook_db", catalog = "")
public class UsuariosRecetasMisRecetasEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idusuarioreceta")
    private int idUsuarioReceta;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="idusuario")
    private UsuariosEntity usuario;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="idreceta")
    private RecetasEntity receta;

    public int getIdUsuarioReceta() {
        return idUsuarioReceta;
    }

    public void setIdUsuarioReceta(int idUsuarioReceta) {
        this.idUsuarioReceta = idUsuarioReceta;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuariosRecetasMisRecetasEntity that = (UsuariosRecetasMisRecetasEntity) o;
        return idUsuarioReceta == that.idUsuarioReceta && Objects.equals(usuario, that.usuario) && Objects.equals(receta, that.receta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuarioReceta, usuario, receta);
    }
}
