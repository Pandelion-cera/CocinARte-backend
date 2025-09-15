package com.api.recetasapi.model;

import com.api.recetasapi.DTO.IngredienteUtilizadoDTO;
import com.api.recetasapi.DTO.PasoDTO;
import com.api.recetasapi.DTO.RecetaDTO;
import com.api.recetasapi.DTO.UsuarioDTO;
import com.api.recetasapi.entities.*;
import com.api.recetasapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class Receta {

    private final RecetasRepository recetasRepository;
    private final TiposRepository tiposRepository;
    private final IngredientesRepository ingredientesRepository;
    private final UsuariosRepository usuariosRepository;
    private final UtilizadosRepository utilizadosRepository;
    private final UnidadesRepository unidadesRepository;
    private final CalificacionesRepository calificacionesRepository;
    private final PasosRepository pasosRepository;
    private final MultimediaRepository multimediaRepository;

    public RecetasEntity modificarReceta(RecetaDTO recetaDTO) {

        RecetasEntity receta = recetasRepository.getById(recetaDTO.getIdReceta());
        recetasRepository.save(receta);
        return receta;
    }

    public List<RecetasEntity> listarRecetas() {
        return recetasRepository.findAll();
    }

    public List<RecetasEntity> listarRecetasVerificadas() {
        return recetasRepository.findVerificadas();
    }
    
    public List<PasosEntity> listarPasosReceta(String receta){
    	return pasosRepository.findPasosByReceta(recetasRepository.getById(Integer.valueOf(receta)));
    }

    public List<RecetasEntity> buscarRecetaPorTexto(String textoBusqueda) {
        List<RecetasEntity> receta = recetasRepository.findByText(textoBusqueda);
        return receta;
    }
    
    public List<RecetasEntity> buscarRecetaPorNombre(String nombre) {
        List<RecetasEntity> receta = recetasRepository.findByName(nombre);
        return receta;
    }

    public List<RecetasEntity> buscarRecetaPorTipo(String tipoBusqueda) {
        TiposEntity tipos = tiposRepository.findByText(tipoBusqueda);
        List<RecetasEntity> recetas =  recetasRepository.findByTipo(tipos);
        return recetas;
    }

    public List<RecetasEntity> buscarRecetaPorIngrediente(String ingrediente) {
        List<IngredientesEntity> ingredientes = ingredientesRepository.findByText(ingrediente);
        List<RecetasEntity> recetas = new ArrayList<RecetasEntity>();
        if(ingredientes.size()!=0){
        	recetas =  utilizadosRepository.findRecetasByIngrediente(ingredientes.get(0));
        }
        return recetas;
    }

    public List<RecetasEntity> buscarRecetaSinIngrediente(String ingrediente) {
        List<IngredientesEntity> ingredientes = ingredientesRepository.findByText(ingrediente);
        List<RecetasEntity> recetas =  utilizadosRepository.findRecetasByIngrediente(ingredientes.get(0));
        List<RecetasEntity> fin = recetasRepository.findVerificadas();
        fin.removeAll(recetas);
        return fin;

    }

    //TODO Validar que la receta no exista para el usuario
    public RecetasEntity nuevaReceta(RecetaDTO recetaDTO) {
        RecetasEntity nuevaReceta = new RecetasEntity();
        usuariosRepository.findById(recetaDTO.getUsuarioCreador().getIdUsuario()).ifPresent(user -> nuevaReceta.setUsuarioCreador(user));
        nuevaReceta.setNombre(recetaDTO.getNombre());
        nuevaReceta.setDescripcion(recetaDTO.getDescripcion());
        nuevaReceta.setFoto(recetaDTO.getFoto());
        nuevaReceta.setPorciones(recetaDTO.getPorciones());
        nuevaReceta.setCantidadPersonas(recetaDTO.getCantidadPersonas());
        tiposRepository.findById(recetaDTO.getTipoReceta().getIdTipo()).ifPresent(tipo -> nuevaReceta.setTipoReceta(tipo));
        nuevaReceta.setFechaCargaCurrent();
        nuevaReceta.setVerificacion((byte) 0);
        RecetasEntity receta = recetasRepository.save(nuevaReceta);


        //TODO Validar que los ingredientes y las unidades existen

        /*List<UtilizadosEntity> ingUtilizados = new ArrayList<>();
        for(IngredienteUtilizadoDTO i:recetaDTO.getIngredientesUtilizados()){
            ingredientesRepository.findById(i.getIdIngrediente()).ifPresent(ing ->
                    ingUtilizados.add(new UtilizadosEntity(ing, i.getCantidad(),
                            unidadesRepository.findById(i.getIdUnidad()).get(), receta)));
        }

        utilizadosRepository.saveAll(ingUtilizados);*/
        return receta;

    }

    public RecetasEntity validarReceta(Integer idReceta, UsuarioDTO usuarioDTO) {
        UsuariosEntity usuario = usuariosRepository.findById(usuarioDTO.getIdUsuario()).get();
        if(!Objects.equals(usuario.getTipoUsuario(), "admin")){
            return null;
        } else {
            RecetasEntity receta = recetasRepository.findById(idReceta).get();
            receta.setVerificacion((byte) 1);
            recetasRepository.save(receta);
            return receta;
        }
    }

    public List<IngredientesEntity> getIngredientesDeLaReceta(Integer idReceta) {
        RecetasEntity receta = recetasRepository.findById(idReceta).get();
        return receta.getIngredientesUtilizados();
    }

    public List<PasosEntity> getPasosDeLaReceta(Integer idReceta) {
        RecetasEntity receta = recetasRepository.findById(idReceta).get();
        return receta.getPasos();
    }
    
    public UtilizadosEntity nuevoIngrediente(IngredienteUtilizadoDTO ing) {
    	UtilizadosEntity fin = new UtilizadosEntity();
    	fin.setCantidad(ing.getCantidad());
    	fin.setUnidad(unidadesRepository.findByName(ing.getIdUnidad()));
    	fin.setIngrediente(ingredientesRepository.findByName(ing.getIdIngrediente()));
    	fin.setReceta(recetasRepository.getById(Integer.valueOf(ing.getIdReceta())));
    	utilizadosRepository.save(fin);
		return fin;
    }
    
    public PasosEntity nuevoPaso(PasoDTO paso) {
    	PasosEntity fin = new PasosEntity();
    	MultimediaEntity mul = new MultimediaEntity();
    	RecetasEntity receta = recetasRepository.getById(Integer.valueOf(paso.getReceta()));
    	fin.setReceta(receta);
    	fin.setTexto(paso.getTexto());
    	fin.setTitulo(paso.getTitulo());
    	fin.setNroPaso(pasosRepository.findPasosByReceta(receta).size() + 1);
    	pasosRepository.save(fin);

    	if(paso.getImagen() != null) {
    		mul.setTipoContenido("foto");
    		mul.setExtension("jpg");
    		mul.setUrlContenido(paso.getImagen());
    		mul.setPaso(pasosRepository.findPasoByRecetaNumero(receta, fin.getNroPaso()).get(0));
    		multimediaRepository.save(mul);
    	}
    	
		return fin;
    }
    
    public void borrarIngrediente(String ingrediente, String receta) {
    	UtilizadosEntity fin = utilizadosRepository.findByBoth(ingredientesRepository.getById(Integer.valueOf(ingrediente)), recetasRepository.getById(Integer.valueOf(receta)));
    	utilizadosRepository.delete(fin);
    }

    public void borrarPaso(PasoDTO paso) {
    	PasosEntity fin = pasosRepository.getById(Integer.valueOf(paso.getIdPaso()));
    	pasosRepository.delete(fin);
    }

    public List<CalificacionesEntity> getCalificacionesDeLaReceta(Integer idReceta) {
        RecetasEntity receta = recetasRepository.findById(idReceta).get();
        return receta.getCalificaciones();
    }

    public List<TiposEntity> listarTiposRecetas() {
        List<TiposEntity> tipos = tiposRepository.findAll();
        return tipos;
    }
    
    public List<UnidadesEntity> listarUnidades() {
        List<UnidadesEntity> unidades = unidadesRepository.findAll();
        return unidades;
    }

    public CalificacionesEntity calificarReceta(Integer idReceta, float autenticidad, float calificacion_numero, UsuarioDTO usuarioDTO) {
        //System.out.println(calificacion_numero);
        String comentarios = new String("EXCELENTE");
        if(calificacion_numero == 4){
            comentarios = "MUY BUENA";
        } else if (calificacion_numero == 3) {
            comentarios = "BUENA";
        } else if (calificacion_numero == 2) {
            comentarios = "REGULAR";
        } else if (calificacion_numero == 1) {
            comentarios = "MALA";
        }

        RecetasEntity receta = recetasRepository.getById(idReceta);
        UsuariosEntity usuario = usuariosRepository.getById(usuarioDTO.getIdUsuario());
        CalificacionesEntity calificacion = calificacionesRepository.findByUsuarioReceta(receta, usuario);

        if(Objects.isNull(calificacion)){
            CalificacionesEntity calificacion_nueva = new CalificacionesEntity();
            calificacion_nueva.setReceta(receta);
            calificacion_nueva.setUsuario(usuario);
            calificacion_nueva.setCalificacion(calificacion_numero);
            calificacion_nueva.setComentarios(comentarios);
            calificacionesRepository.save(calificacion_nueva);
            return calificacion_nueva;
        } else {
            calificacion.setCalificacion(calificacion_numero);
            calificacion.setComentarios(comentarios);
            calificacionesRepository.save(calificacion);
            return calificacion;
        }
    }

    public List<RecetasEntity> listarRecetasVerificadasByFecha() {
        List<RecetasEntity> recetasVerificadas =  recetasRepository.findVerificadas();
        return recetasRepository.findVerificadasOrderByFechaDesc();
    }

    public List<RecetasEntity> listarRecetasVerificadasByUsuario() {
        List<RecetasEntity> recetasVerificadas =  recetasRepository.findVerificadas();
        return recetasRepository.findVerificadasOrderByUsuario();
    }

    public void EliminarReceta(Integer idReceta) {
        RecetasEntity receta = recetasRepository.getById(idReceta);
        recetasRepository.deleteById(receta.getIdReceta());
    }
}



