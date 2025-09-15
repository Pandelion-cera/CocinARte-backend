package com.api.recetasapi.repository;

import com.api.recetasapi.entities.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Integer> {

    @Query("FROM UsuariosEntity u WHERE u.idUsuario = :idUsuario")
    UsuariosEntity getById(Integer idUsuario);

    @Query("FROM UsuariosEntity u WHERE u.nickname = :nickname")
    UsuariosEntity getByNickname(String nickname);

    @Query("FROM UsuariosEntity u WHERE u.mail = :mail")
    UsuariosEntity getByMail(String mail);

}
