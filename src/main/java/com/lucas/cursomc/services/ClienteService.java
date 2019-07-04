package com.lucas.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.lucas.cursomc.dto.CategoriaDTO;
import com.lucas.cursomc.dto.ClienteDTO;
import com.lucas.cursomc.services.exception.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lucas.cursomc.domain.Categoria;
import com.lucas.cursomc.domain.Cliente;
import com.lucas.cursomc.repositories.ClienteRepository;
import com.lucas.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired	
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "+ Cliente.class.getName()));
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);

	}

	public Cliente update(Cliente obj) {
		Cliente cliente = find(obj.getId());
		updateData(cliente,obj);
		return repo.save(cliente);
	}

	private void updateData(Cliente cliente, Cliente obj) {

		cliente.setNome(obj.getNome());
		cliente.setEmail(obj.getEmail());

	}


	public void delete(Integer id) {
		find(id);
		try{
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas!");
		}
	}

	public List<Cliente> findAll(){

		return repo.findAll();

	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDTO) {

		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(),null, null);
	}
}
