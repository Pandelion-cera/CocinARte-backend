package com.api.recetasapi.controller;

import com.api.recetasapi.DTO.*;
import com.api.recetasapi.entities.LocalizacionEntity;
import com.api.recetasapi.entities.RecetasEntity;
import com.api.recetasapi.entities.UsuariosEntity;
import com.api.recetasapi.model.Usuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuario")
public class UsuariosController {

    private final Usuarios usuarios;

    @GetMapping("/all")
    public ResponseEntity<List<UsuariosEntity>> getAllUsuarios(){
        List<UsuariosEntity> usuarios = this.usuarios.listarUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{UsuarioMailOnombre}")
    public ResponseEntity<UsuariosEntity> getUsuario(@PathVariable String UsuarioMailOnombre){
        UsuariosEntity usuario;
        if(UsuarioMailOnombre.contains("@")){
            usuario = this.usuarios.getUsuarioPorEmail(UsuarioMailOnombre);
        }
        else usuario = this.usuarios.getUsuarioPorNombre(UsuarioMailOnombre);
        if(!Objects.isNull(usuario)){
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/{idUsuario}/misrecetas")
    public ResponseEntity<List<RecetasEntity>> getMisRecetasCargadas(@PathVariable Integer idUsuario){
        List<RecetasEntity> misRecetasCargadas = this.usuarios.listarMisRecetasCargadas(idUsuario);
        return new ResponseEntity<>(misRecetasCargadas, HttpStatus.OK);
    }

    @GetMapping("{idUsuario}/recetasporhacer")
    public ResponseEntity<List<RecetasEntity>> getMisRecetasPorHacer(@PathVariable Integer idUsuario){
        List<RecetasEntity> misRecetasPorHacer = this.usuarios.listarMisRecetasPorHacer(idUsuario);
        return new ResponseEntity<>(misRecetasPorHacer, HttpStatus.OK);
    }

    @GetMapping("{idUsuario}/validarrecetasporhacer")
    public ResponseEntity<Boolean> ValidarNombreRecetaPorUsuarioCreador(@PathVariable Integer idUsuario, @RequestParam String nombreReceta){
        return new ResponseEntity<>(this.usuarios.validarNombreReceta(idUsuario, nombreReceta), HttpStatus.OK);
    }

    @PostMapping("/{idUsuario}/misrecetasporhacer")
    public ResponseEntity<String> AgregarAMisRecetasPorHacer(@PathVariable Integer idUsuario, @RequestBody RecetaDTO recetaDTO){
        this.usuarios.agregarAMiWishlist(idUsuario, recetaDTO);
        return ResponseEntity.ok("Receta agregada correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<UsuariosEntity> Login(@RequestBody LoginDTO login){
        UsuariosEntity usuario = this.usuarios.login(login);

        if(this.usuarios.login(login) != null){
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            System.out.println("Paso por aca");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/validarCuenta")
    public ResponseEntity<Boolean> ValidarCuenta(@RequestBody ValidacionDTO validacionDTO){
        Boolean validado = this.usuarios.validarCuenta(validacionDTO);
        if(validado){
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuariosEntity> RegistrarCuenta(@RequestBody UsuarioDTO usuarioDTO) throws MessagingException, UnsupportedEncodingException {
        UsuariosEntity usuario_nuevo = this.usuarios.registrarCuenta(usuarioDTO);
        this.usuarios.setVerificationCodeAndSendMail(usuarioDTO);
        if(!Objects.isNull(usuario_nuevo)){
            return new ResponseEntity<>(usuario_nuevo, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/actualizar")
    public ResponseEntity<UsuariosEntity> ActualizarCuenta(@RequestBody UsuarioDTO usuarioDTO){
        UsuariosEntity usuario_actualizado = this.usuarios.actualizarCuenta(usuarioDTO);
        if(!Objects.isNull(usuario_actualizado)){
            return new ResponseEntity<>(usuario_actualizado, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/cambiarPassword")
    public ResponseEntity<UsuariosEntity> cambiarPassword(@RequestBody UsuarioDTO usuarioDTO){
        UsuariosEntity usuario_actualizado = this.usuarios.cambiarPassword(usuarioDTO);
        if(!Objects.isNull(usuario_actualizado)){
            return new ResponseEntity<>(usuario_actualizado, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/verificationcode")
    public ResponseEntity<UsuariosEntity> EnviarVerificationCode(@RequestBody UsuarioDTO usuarioDTO) throws MessagingException, UnsupportedEncodingException {
        UsuariosEntity usuario = this.usuarios.setVerificationCodeAndSendMail(usuarioDTO);
        if(!Objects.isNull(usuario)){
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idUsuario}/misrecetasporhacer")
    public ResponseEntity<String> BorrarDeMisRecetasPorHacer(@PathVariable Integer idUsuario, @RequestBody RecetaDTO recetaDTO){
        this.usuarios.BorrarDeMiWishlist(idUsuario, recetaDTO);
        return ResponseEntity.ok("Receta borrada correctamente");
    }
    
    @GetMapping("/creadores")
    public ResponseEntity<List<UsuariosEntity>> getUsuariosCreadores(){
        List<UsuariosEntity> usuarios = this.usuarios.listarUsuariosCreadores();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
}
