package com.api.recetasapi.DTO;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Integer idUsuario;
    private String nombre;
    private String nickname;
    private String email;
    private String password;
    private String verificationCode;
}
