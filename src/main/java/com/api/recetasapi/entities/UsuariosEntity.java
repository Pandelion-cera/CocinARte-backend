package com.api.recetasapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuarios", schema = "cookbook_db", catalog = "")

public class UsuariosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idusuario")
    private int idUsuario;
    @Basic
    @Column(name = "mail")
    private String mail;
    @Basic
    @Column(name = "nickname")
    private String nickname;
    @Basic
    @Column(name = "habilitado")
    private String habilitado;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "avatar")
    private String avatar;
    @Basic
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    @Basic
    @JsonIgnore
    @Column(name = "codigo_verificacion")
    private String codigoVerificacion;
    @OneToMany(mappedBy = "usuario")
    private List<LocalizacionEntity> localizaciones;


    @Basic
    @Column(name = "contraseña")
    private String constraseña;

    @OneToMany(mappedBy = "usuarioCreador",cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
    private List<RecetasEntity> recetasCargadas;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name="usuarios_recetas_mis_recetas", joinColumns = @JoinColumn(name="idusuario"), inverseJoinColumns = @JoinColumn(name="idreceta"))
    private List<RecetasEntity> misRecetasPorHacer;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getConstraseña() { return constraseña; }

    public void setConstraseña(String constraseña) { this.constraseña = constraseña; }

    public List<RecetasEntity> getRecetasCargadas() { return recetasCargadas; }

    public void setRecetasCargadas(List<RecetasEntity> recetasCargadas) { this.recetasCargadas = recetasCargadas; }

    public List<RecetasEntity> getMisRecetasPorHacer() { return misRecetasPorHacer; }

    public void setMisRecetasPorHacer(List<RecetasEntity> misRecetasPorHacer) { this.misRecetasPorHacer = misRecetasPorHacer; }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuariosEntity that = (UsuariosEntity) o;
        return idUsuario == that.idUsuario && Objects.equals(mail, that.mail) && Objects.equals(nickname, that.nickname) && Objects.equals(habilitado, that.habilitado) && Objects.equals(nombre, that.nombre) && Objects.equals(avatar, that.avatar) && Objects.equals(tipoUsuario, that.tipoUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, mail, nickname, habilitado, nombre, avatar, tipoUsuario);
    }
}
