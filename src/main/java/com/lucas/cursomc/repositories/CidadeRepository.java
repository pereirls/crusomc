package com.lucas.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucas.cursomc.domain.Cidade;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

    @Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :idEstado ORDER BY obj.nome")
    public List<Cidade> findCidades(@Param("idEstado") Integer idEstado);

}
