package com.lucas.cursomc.services;

import com.lucas.cursomc.domain.Cidade;
import com.lucas.cursomc.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    CidadeRepository cidadeRepository;

    public List<Cidade> findByEstado(Integer idEstado) {
        return cidadeRepository.findCidades(idEstado);
    }
}
