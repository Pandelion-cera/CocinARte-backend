package com.api.recetasapi.model;

import com.api.recetasapi.DTO.IngredienteDTO;
import com.api.recetasapi.DTO.IngredienteEnLocalizacionDTO;
import com.api.recetasapi.DTO.IngredienteLocalizadoDTO;
import com.api.recetasapi.DTO.LocalizacionDTO;
import com.api.recetasapi.entities.IngredienteLocalizadoEntity;
import com.api.recetasapi.entities.IngredientesEntity;
import com.api.recetasapi.entities.LocalizacionEntity;
import com.api.recetasapi.repository.IngredienteLocalizadoRepository;
import com.api.recetasapi.repository.IngredientesRepository;
import com.api.recetasapi.repository.LocalizacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class Localizacion {

    private final LocalizacionRepository localizacionRepository;
    private final IngredienteLocalizadoRepository ingredienteLocalizadoRepository;
    private final IngredientesRepository ingredientesRepository;

    public LocalizacionEntity nuevaLocalizacion(LocalizacionDTO localizacionDTO) {
        LocalizacionEntity nuevoEntity = new LocalizacionEntity();
        nuevoEntity.setNombre(localizacionDTO.getNombre());
        nuevoEntity.setDireccion(localizacionDTO.getDireccion());
        nuevoEntity.setLatitud(localizacionDTO.getLatitud());
        nuevoEntity.setLongitud(localizacionDTO.getLongitud());
        LocalizacionEntity nuevo = localizacionRepository.save(nuevoEntity);
        return nuevo;
    }

    public void agregarCorroboracion(IngredienteLocalizadoDTO ing){
        IngredienteLocalizadoEntity fin = ingredienteLocalizadoRepository.findByBoth(ing.getIdIngrediente(), ing.getIdLocalizacion());
        fin.setConfiabilidad(fin.getConfiabilidad() + 1);
        ingredienteLocalizadoRepository.save(fin);
    }

    public List<IngredienteEnLocalizacionDTO> encontrarIngredientesEnLocalizacion(int idIngrediente, double latitud, double longitud, double distancia){
        return ingredienteLocalizadoRepository.buscarLocalizacionesConIngredienteYConfiabilidad(idIngrediente, latitud, longitud, distancia);
    }

    public void agregarIngredienteLocalizado(IngredienteLocalizadoDTO ing){
        IngredienteLocalizadoEntity fin = new IngredienteLocalizadoEntity(localizacionRepository.findById(ing.getIdLocalizacion()),ingredientesRepository.findById(ing.getIdIngrediente()), 1);
        ingredienteLocalizadoRepository.save(fin);
    }

}
