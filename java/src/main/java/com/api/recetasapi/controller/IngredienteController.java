package com.api.recetasapi.controller;

import com.api.recetasapi.DTO.IngredienteDTO;
import com.api.recetasapi.DTO.RecetaDTO;
import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.RecetasEntity;
import com.api.recetasapi.model.Ingrediente;
import com.api.recetasapi.model.Receta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/ingredientes")
public class IngredienteController {
    private final Ingrediente ingredientes;

    @GetMapping("/all")
    public ResponseEntity<List<IngredientesEntity>> getAllIngredientes(){
        return new ResponseEntity<>(this.ingredientes.listarIngredientes(), HttpStatus.OK);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<IngredientesEntity> getAllIngredientes(@RequestBody IngredienteDTO nuevoIngrediente){
        return new ResponseEntity<>(this.ingredientes.nuevoIngrediente(nuevoIngrediente), HttpStatus.OK);
    }
    
    @GetMapping("/recetaIngredientes")
    public ResponseEntity<List<IngredientesEntity>> getRecetaIngredientes(@RequestParam String receta){
    	Integer sen = Integer.valueOf(receta);
        return new ResponseEntity<>(this.ingredientes.listarIngredientesReceta(sen), HttpStatus.OK);
    }
}
