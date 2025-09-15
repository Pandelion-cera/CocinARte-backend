package com.api.recetasapi.controller;

import com.api.recetasapi.DTO.IngredienteEnLocalizacionDTO;
import com.api.recetasapi.DTO.IngredienteLocalizadoDTO;
import com.api.recetasapi.DTO.LocalizacionDTO;
import com.api.recetasapi.entities.LocalizacionEntity;
import com.api.recetasapi.model.Localizacion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/localizaciones")
public class LocalizacionController {

    private final Localizacion localizacion;

    @PostMapping("/agregarlocalizacion")
    public ResponseEntity<LocalizacionEntity> agregarLocalizacion(@RequestBody LocalizacionDTO localizacion){
        return new ResponseEntity<>(this.localizacion.nuevaLocalizacion(localizacion), HttpStatus.OK);
    }

    @PostMapping("/corroboraringrediente")
    public void corroborarIngrediente(@RequestBody IngredienteLocalizadoDTO zona){
        localizacion.agregarCorroboracion(zona);
    }

    @GetMapping("/encontraringrediente")
    public ResponseEntity<List<IngredienteEnLocalizacionDTO>> encontrarIngredienteEnLocalizacion(@RequestParam int idIngrediente, @RequestParam double latitud, @RequestParam double longitud, @RequestParam double distancia){
        return new ResponseEntity<>(localizacion.encontrarIngredientesEnLocalizacion(idIngrediente, latitud, longitud, distancia), HttpStatus.OK);
    }

    @PostMapping("/agregarlocalizacioningrediente")
    public void agregarIngredienteLocalizado(@RequestBody IngredienteLocalizadoDTO zona){
        localizacion.agregarIngredienteLocalizado(zona);
    }

}
