package com.lucas.cursomc.services;

import com.lucas.cursomc.Security.UserSS;
import com.lucas.cursomc.domain.Cidade;
import com.lucas.cursomc.domain.Cliente;
import com.lucas.cursomc.domain.Endereco;
import com.lucas.cursomc.domain.enuns.Perfil;
import com.lucas.cursomc.domain.enuns.TipoCliente;
import com.lucas.cursomc.dto.ClienteDTO;
import com.lucas.cursomc.dto.ClienteNewDTO;
import com.lucas.cursomc.repositories.CidadeRepository;
import com.lucas.cursomc.repositories.ClienteRepository;
import com.lucas.cursomc.repositories.EnderecoRepository;
import com.lucas.cursomc.services.exception.AuthorizationException;
import com.lucas.cursomc.services.exception.DataIntegrityException;
import com.lucas.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
	
	@Autowired	
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente find(Integer id) {
		UserSS usuarioLogado = UserService.authenticated();
		if(usuarioLogado==null || !usuarioLogado.hasRole(Perfil.ADMIN) && !id.equals(usuarioLogado.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "+ Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return  obj;

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
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas! ");
		}
	}

	public List<Cliente> findAll(){

		return repo.findAll();

	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente froDTO(ClienteDTO objDTO) {
		return new Cliente(null, objDTO.getNome(), objDTO.getEmail(), null, null,null);
	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {

		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipoCliente()),encoder.encode(objDTO.getSenha()));
		Optional<Cidade> cid = cidadeRepository.findById(objDTO.getIdCidade());
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid.get());
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2()!= null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3()!= null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}

		return cli;
	}
}
