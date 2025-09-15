package com.api.recetasapi.model;

import com.api.recetasapi.DTO.IngredienteDTO;
import com.api.recetasapi.DTO.RecetaDTO;
import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.RecetasEntity;
import com.api.recetasapi.repository.IngredientesRepository;
import com.api.recetasapi.repository.RecetasRepository;
import com.api.recetasapi.repository.UtilizadosRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class Ingrediente {

    private final IngredientesRepository ingredientesRepository;
    private final RecetasRepository recetasRepository;
    private final UtilizadosRepository utilizadosRepository;

    public List<IngredientesEntity> listarIngredientes() {
        return ingredientesRepository.findAll();
    }

    public IngredientesEntity nuevoIngrediente(IngredienteDTO nuevoIngredienteDTO) {
        IngredientesEntity nuevoIngrediente = new IngredientesEntity();
        nuevoIngrediente.setNombre(nuevoIngredienteDTO.getNombre());
        IngredientesEntity nuevo = ingredientesRepository.save(nuevoIngrediente);
        return nuevo;
    }
    
    public List<IngredientesEntity> listarIngredientesReceta(Integer receta) {
    	RecetasEntity fin = recetasRepository.getById(receta);
        return utilizadosRepository.findIngredientesByReceta(fin);
    }
}
