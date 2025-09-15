package com.api.recetasapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.recetasapi.entities.MultimediaEntity;
import com.api.recetasapi.entities.PasosEntity;

public interface MultimediaRepository extends JpaRepository<MultimediaEntity, Integer> {

}
