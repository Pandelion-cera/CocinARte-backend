package com.api.recetasapi.model;

import com.api.recetasapi.DTO.LoginDTO;
import com.api.recetasapi.DTO.RecetaDTO;
import com.api.recetasapi.DTO.UsuarioDTO;
import com.api.recetasapi.DTO.ValidacionDTO;
import com.api.recetasapi.entities.RecetasEntity;
import com.api.recetasapi.entities.UsuariosEntity;
import com.api.recetasapi.repository.RecetasRepository;
import com.api.recetasapi.repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class Usuarios {



    private final UsuariosRepository usuariosRepository;

    private final RecetasRepository recetasRepository;

    @Autowired
    private JavaMailSender mailSender;

    public UsuariosEntity modificarUsuario(Integer idUsuario, String nombre, String nickname) {

        UsuariosEntity usuario = usuariosRepository.getById(idUsuario);
        usuario.setNombre(nombre);
        usuario.setNickname(nickname);

        usuariosRepository.save(usuario);
        return usuario;
    }

    public void eliminarUsuario(Integer idUsuario) {
        UsuariosEntity usuario = usuariosRepository.getById(idUsuario);
        usuariosRepository.delete(usuario);
    }

    public List<RecetasEntity> listarRecetas() {
        return recetasRepository.findAll();
    }

    public List<UsuariosEntity> listarUsuarios() {
        return usuariosRepository.findAll();
    }
    
    public List<UsuariosEntity> listarUsuariosCreadores() {
        return recetasRepository.findCreators();
    }

    public UsuariosEntity crearUsuario(UsuarioDTO nuevoUsuario) {
        UsuariosEntity nuevo = new UsuariosEntity();
        nuevo.setNombre(nuevoUsuario.getNombre());
        nuevo.setNickname(nuevoUsuario.getNickname());
        usuariosRepository.save(nuevo);
        return nuevo;
    }

    public UsuariosEntity verUsuario(Integer idUsuario) {
        return usuariosRepository.getById(idUsuario);
    }

    public void habilitarUsuario(Integer idUsuario) {
        UsuariosEntity usuario = usuariosRepository.getById(idUsuario);
        usuario.setHabilitado("Si");

    }

    public List<RecetasEntity> listarMisRecetasCargadas(Integer idUsuario) {
        UsuariosEntity usuario = usuariosRepository.getById(idUsuario);
        return usuario.getRecetasCargadas();
    }

    public List<RecetasEntity> listarMisRecetasPorHacer(Integer idUsuario) {
        UsuariosEntity usuario = usuariosRepository.findById(idUsuario).get();
        return usuario.getMisRecetasPorHacer();
    }

    public void agregarAMiWishlist(Integer idUsuario, RecetaDTO recetaDTO) {
        UsuariosEntity usuario = usuariosRepository.findById(idUsuario).get();
        RecetasEntity receta = recetasRepository.findById(recetaDTO.getIdReceta()).get();

        List<RecetasEntity> misRecetasPorHacer = usuario.getMisRecetasPorHacer();
        if(!misRecetasPorHacer.contains(receta)){
            misRecetasPorHacer.add(receta);
            usuario.setMisRecetasPorHacer(misRecetasPorHacer);
            usuariosRepository.save(usuario);
        }
    }

    public void BorrarDeMiWishlist(Integer idUsuario, RecetaDTO recetaDTO) {
        UsuariosEntity usuario = usuariosRepository.findById(idUsuario).get();
        RecetasEntity receta = recetasRepository.findById(recetaDTO.getIdReceta()).get();

        List<RecetasEntity> misRecetasPorHacer = usuario.getMisRecetasPorHacer();
        if(misRecetasPorHacer.contains(receta)){
            misRecetasPorHacer.remove(receta);
            usuario.setMisRecetasPorHacer(misRecetasPorHacer);
            usuariosRepository.save(usuario);
        }
    }

    public Boolean validarNombreReceta(Integer idUsuario, String nombreReceta) {
        UsuariosEntity usuario = usuariosRepository.findById(idUsuario).get();
        List<RecetasEntity> recetas = usuario.getRecetasCargadas();
        for(RecetasEntity r:recetas){
            if(Objects.equals(r.getNombre(), nombreReceta)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public UsuariosEntity login(LoginDTO login) {
        if (login.getUsername().contains("@")) {
            UsuariosEntity usuario = usuariosRepository.getByMail(login.getUsername());

            if (!Objects.isNull(usuario) && usuario.getConstraseña().equals(login.getPassword())) {
                return usuario;
            } else {
                return null;
            }
        } else {
            UsuariosEntity usuario = usuariosRepository.getByNickname(login.getUsername());
            if (!Objects.isNull(usuario) && usuario.getConstraseña().equals(login.getPassword())) {
                return usuario;
            } else {
                return null;
            }
        }
    }

    public Boolean validarCuenta(ValidacionDTO validacionDTO) {

        UsuariosEntity usuario;
        if(!validacionDTO.getUsername().equals(""))
            usuario = usuariosRepository.getByNickname(validacionDTO.getUsername());
        else usuario = usuariosRepository.getByMail(validacionDTO.getEmail());
        if (!Objects.isNull(usuario) && usuario.getCodigoVerificacion().equals(validacionDTO.getCodigo())) {
            usuario.setHabilitado("Si");
            usuariosRepository.save(usuario);
            return true;
        } else {
            return false;
        }
    }

    public UsuariosEntity getUsuarioPorNombre(String nombreUsuario) {
        UsuariosEntity usuario = usuariosRepository.getByNickname(nombreUsuario);
        return usuario;
    }

    public UsuariosEntity getUsuarioPorEmail(String emailUsuario){
        UsuariosEntity usuario = usuariosRepository.getByMail(emailUsuario);
        return usuario;
    }

    public UsuariosEntity registrarCuenta(UsuarioDTO usuarioDTO) throws MessagingException, UnsupportedEncodingException {
        UsuariosEntity usuario_nuevo = new UsuariosEntity();
        usuario_nuevo.setHabilitado("No");
        usuario_nuevo.setNickname(usuarioDTO.getNickname());
        usuario_nuevo.setNombre("");
        usuario_nuevo.setMail(usuarioDTO.getEmail());
        usuario_nuevo.setConstraseña(usuarioDTO.getPassword());
        //usuario_nuevo.setCodigoVerificacion(generateVerifiactionCode());
        usuariosRepository.save(usuario_nuevo); // guarda en la base de datos
        return usuario_nuevo;
    }

    public UsuariosEntity actualizarCuenta(UsuarioDTO usuarioDTO) {
        UsuariosEntity usuario_nuevo = usuariosRepository.getById(usuarioDTO.getIdUsuario()) ;
        usuario_nuevo.setNombre(usuarioDTO.getNombre());
        usuario_nuevo.setConstraseña(usuarioDTO.getPassword());
        //Random rand = new Random();
        //usuario_nuevo.setCodigoVerificacion(rand.nextInt(100000, 999999));
        usuariosRepository.save(usuario_nuevo);
        return usuario_nuevo;
    }

    public UsuariosEntity cambiarPassword(UsuarioDTO usuarioDTO) {
        UsuariosEntity usuario_nuevo;
        if(!usuarioDTO.getNickname().equals("")){
            usuario_nuevo = usuariosRepository.getByNickname(usuarioDTO.getNickname());
        }
        else usuario_nuevo = usuariosRepository.getByMail(usuarioDTO.getEmail());
        usuario_nuevo.setConstraseña(usuarioDTO.getPassword());
        usuariosRepository.save(usuario_nuevo);
        return usuario_nuevo;
    }

    public UsuariosEntity setVerificationCodeAndSendMail(UsuarioDTO usuarioDTO) throws MessagingException, UnsupportedEncodingException {
        UsuariosEntity usuario;
        if(!usuarioDTO.getNickname().equals("")){
            usuario = usuariosRepository.getByNickname(usuarioDTO.getNickname());
        }
        else usuario = usuariosRepository.getByMail(usuarioDTO.getEmail());
        usuario.setCodigoVerificacion(generateVerifiactionCode());
        sendVerificationEmail(usuario);
        usuariosRepository.save(usuario);
        return usuario;
    }

    public void sendVerificationEmail(UsuariosEntity usuario) throws MessagingException, UnsupportedEncodingException {
        String subject = "Verificación de la cuenta";
        String senderName = "Cookbook App";
        String mailContent = "<p>Estimado " + usuario.getNickname() + ",<br></p>";
        mailContent += "<p>verifique su cuenta con el siguiente código de 6 cifras: <br>" + "<h1>" + usuario.getCodigoVerificacion() + "</h1>" + "</p>";
        mailContent += "<p>Un saludo, <br><h4>The Cookbook Team</h4></p>";

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setFrom("cookbook.app.customerservice@gmail.com",senderName);
        helper.setTo(usuario.getMail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(msg);
    }

    public String generateVerifiactionCode(){
        int number = new Random().nextInt(999999);
        return String.format("%06d", number);
    }
}
