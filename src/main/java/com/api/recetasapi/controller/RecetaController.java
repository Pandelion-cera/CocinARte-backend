package com.api.recetasapi.controller;

import com.api.recetasapi.DTO.IngredienteUtilizadoDTO;
import com.api.recetasapi.DTO.PasoDTO;
import com.api.recetasapi.DTO.RecetaDTO;
import com.api.recetasapi.DTO.UsuarioDTO;
import com.api.recetasapi.entities.*;
import com.api.recetasapi.model.Receta;
import com.api.recetasapi.model.Usuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/recetas")
public class RecetaController {

    private final Receta recetas;

    @GetMapping("/all")
    public ResponseEntity<List<RecetasEntity>> getAllRecetas(){

        return new ResponseEntity<>(this.recetas.listarRecetas(), HttpStatus.OK);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<TiposEntity>> getAllTiposRecetas(){
        return new ResponseEntity<>(this.recetas.listarTiposRecetas(), HttpStatus.OK);
    }

    @GetMapping("/verificadas")
    public ResponseEntity<List<RecetasEntity>> getAllRecetasVerificadas(){
        return new ResponseEntity<>(this.recetas.listarRecetasVerificadas(), HttpStatus.OK);
    }

    @GetMapping("/verificadasByFecha")
    public ResponseEntity<List<RecetasEntity>> getAllRecetasVerificadasByFecha(){
        return new ResponseEntity<>(this.recetas.listarRecetasVerificadasByFecha(), HttpStatus.OK);
    }

    @GetMapping("/verificadasByUsuario")
    public ResponseEntity<List<RecetasEntity>> getAllRecetasVerificadasByUsuario(){
        return new ResponseEntity<>(this.recetas.listarRecetasVerificadasByUsuario(), HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<RecetasEntity>> findRecetaByText(@RequestParam String textoBusqueda){
        return new ResponseEntity<>(this.recetas.buscarRecetaPorTexto(textoBusqueda), HttpStatus.OK);
    }
    
    @GetMapping("/existe")
    public ResponseEntity<List<RecetasEntity>> comprobarExistencia(@RequestParam String nombre){
        return new ResponseEntity<>(this.recetas.buscarRecetaPorNombre(nombre), HttpStatus.OK);
    }
    
    @GetMapping("/unidades")
    public ResponseEntity<List<UnidadesEntity>> findUnidades(){
        return new ResponseEntity<>(this.recetas.listarUnidades(), HttpStatus.OK);
    }

    @GetMapping("/buscarportipo")
    public ResponseEntity<List<RecetasEntity>> findRecetaByTipo(@RequestParam String tipoBusqueda){
        return new ResponseEntity<>(this.recetas.buscarRecetaPorTipo(tipoBusqueda), HttpStatus.OK);
    }

    @GetMapping("/buscarporingrediente")
    public ResponseEntity<List<RecetasEntity>> findRecetaByIngrediente(@RequestParam String ingrediente){
        return new ResponseEntity<>(this.recetas.buscarRecetaPorIngrediente(ingrediente), HttpStatus.OK);
    }

    @GetMapping("/buscarsiningrediente")
    public ResponseEntity<List<RecetasEntity>> findRecetaSinIngrediente(@RequestParam String ingrediente){
        return new ResponseEntity<>(this.recetas.buscarRecetaSinIngrediente(ingrediente), HttpStatus.OK);
    }

    //Receta NuevaReceta(Receta receta){}
    @PostMapping("/nueva")
    public ResponseEntity<RecetasEntity> nuevaReceta(@RequestBody RecetaDTO recetaNueva){
        return new ResponseEntity<>(this.recetas.nuevaReceta(recetaNueva), HttpStatus.OK);
    }
    
    @PostMapping("/utilizarIngrediente")
    public ResponseEntity<UtilizadosEntity> nuevoIngrediente(@RequestBody IngredienteUtilizadoDTO ingredienteNueva){
        return new ResponseEntity<>(this.recetas.nuevoIngrediente(ingredienteNueva), HttpStatus.OK);
    }
    
    @GetMapping("/pasos")
    public ResponseEntity<List<PasosEntity>> getRecetaPasos(@RequestParam String receta){
        return new ResponseEntity<>(this.recetas.listarPasosReceta(receta), HttpStatus.OK);
    }
    
    @PostMapping("/nuevoPaso")
    public ResponseEntity<PasosEntity> nuevoPaso(@RequestBody PasoDTO paso){
    	System.out.println(paso.getImagen());
        return new ResponseEntity<>(this.recetas.nuevoPaso(paso), HttpStatus.OK);
    }
    
    @PostMapping("/borrarIngrediente")
    public ResponseEntity<String> borrarIngrediente(@RequestBody IngredienteUtilizadoDTO ing){
    	this.recetas.borrarIngrediente(ing.getIdIngrediente(), ing.getIdReceta());
    	return ResponseEntity.ok("Ingrediente borrado correctamente");
    }
    
    @PostMapping("/borrarPaso")
    public ResponseEntity<String> borrarPaso(@RequestBody PasoDTO paso){
    	this.recetas.borrarPaso(paso);
    	return ResponseEntity.ok("Ingrediente borrado correctamente");
    }

    //TODO Modificar Receta
    //Receta ModificarReceta(Receta receta){}

    //TODO Borrar Receta
    //Receta BorrarReceta(Receta receta){}
    @DeleteMapping("/{idReceta}/eliminar")
    public ResponseEntity<String> EliminarReceta(@PathVariable Integer idReceta){
        this.recetas.EliminarReceta(idReceta);
        return ResponseEntity.ok("Receta borrada correctamente");
    }

    @PostMapping("/{idReceta}/validar")
    public ResponseEntity<RecetasEntity> ValidarReceta(@PathVariable Integer idReceta, @RequestBody UsuarioDTO usuario){
        return new ResponseEntity<>(this.recetas.validarReceta(idReceta, usuario), HttpStatus.OK);
    }

    @PostMapping("/{idReceta}/calificar/{calificacion}")
    public ResponseEntity<CalificacionesEntity> CalificarReceta(@PathVariable Integer idReceta, @PathVariable float autenticidad, @PathVariable float calificacion, @RequestBody UsuarioDTO usuario){
        //System.out.println(calificacion);
        return new ResponseEntity<>(this.recetas.calificarReceta(idReceta, autenticidad, calificacion, usuario), HttpStatus.OK);
    }
}
