package com.lucas.cursomc.resources;

import com.lucas.cursomc.domain.Cidade;
import com.lucas.cursomc.domain.Estado;
import com.lucas.cursomc.dto.CidadeDTO;
import com.lucas.cursomc.dto.EstadoDTO;
import com.lucas.cursomc.services.CidadeService;
import com.lucas.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    EstadoService estadoService;

    @Autowired
    CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> list = estadoService.findAll();
        List<EstadoDTO> listDTO = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @RequestMapping(value = "/{idEstado}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findByEstado(@PathVariable Integer idEstado) {
        List<Cidade> list = cidadeService.findByEstado(idEstado);
        List<CidadeDTO> listDTO = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

}
