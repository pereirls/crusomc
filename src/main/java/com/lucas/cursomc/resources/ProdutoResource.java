package com.lucas.cursomc.resources;

import com.lucas.cursomc.domain.Produto;
import com.lucas.cursomc.domain.Produto;
import com.lucas.cursomc.dto.ProdutoDTO;
import com.lucas.cursomc.resources.utils.URL;
import com.lucas.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = produtoService.buscar(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method=RequestMethod.GET)

	public ResponseEntity<Page> findPage(@RequestParam(value="nome", defaultValue = "") String nome,
										 @RequestParam(value="categorias", defaultValue = "") String categorias,
										 @RequestParam(value="page", defaultValue = "0") Integer page,
										 @RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage,
										 @RequestParam(value="orderBy", defaultValue = "nome")String orderBy,
										 @RequestParam(value="direction", defaultValue = "ASC")String direction) {
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<Produto> list = produtoService.search(nomeDecoded,ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj ->  new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}
