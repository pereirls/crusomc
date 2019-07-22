package com.lucas.cursomc.resources;

import com.lucas.cursomc.domain.Cliente;
import com.lucas.cursomc.dto.ClienteDTO;
import com.lucas.cursomc.dto.ClienteNewDTO;
import com.lucas.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = clienteService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO) {

		Cliente obj = clienteService.fromDTO(objDTO);
		obj = clienteService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid  @RequestBody ClienteNewDTO objDTO, @PathVariable Integer id){
		Cliente obj = clienteService.fromDTO(objDTO);
		obj.setId(id);
		obj =  clienteService.update(obj);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = clienteService.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj ->  new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity<Page> findPage(@RequestParam(value="page", defaultValue = "0") Integer page,
										 @RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage,
										 @RequestParam(value="orderBy", defaultValue = "nome")String orderBy,
										 @RequestParam(value="direction", defaultValue = "ASC")String direction) {
		Page<Cliente> list = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj ->  new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}
