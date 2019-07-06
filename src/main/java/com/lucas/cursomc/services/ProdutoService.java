package com.lucas.cursomc.services;

import com.lucas.cursomc.domain.Categoria;
import com.lucas.cursomc.domain.Pedido;
import com.lucas.cursomc.domain.Produto;
import com.lucas.cursomc.repositories.CategoriaRepository;
import com.lucas.cursomc.repositories.PedidoRepository;
import com.lucas.cursomc.repositories.ProdutoRepository;
import com.lucas.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
	
	@Autowired	
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto buscar(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "+ Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
	}
}
