package com.lucas.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucas.cursomc.domain.Categoria;
import com.lucas.cursomc.domain.Cidade;
import com.lucas.cursomc.domain.Cliente;
import com.lucas.cursomc.domain.Endereco;
import com.lucas.cursomc.domain.Estado;
import com.lucas.cursomc.domain.ItemPedido;
import com.lucas.cursomc.domain.Pagamento;
import com.lucas.cursomc.domain.PagamentoComBoleto;
import com.lucas.cursomc.domain.PagamentoComCartao;
import com.lucas.cursomc.domain.Pedido;
import com.lucas.cursomc.domain.Produto;
import com.lucas.cursomc.domain.enuns.EstadoPagamento;
import com.lucas.cursomc.domain.enuns.TipoCliente;
import com.lucas.cursomc.repositories.CategoriaRepository;
import com.lucas.cursomc.repositories.CidadeRepository;
import com.lucas.cursomc.repositories.ClienteRepository;
import com.lucas.cursomc.repositories.EnderecoRepository;
import com.lucas.cursomc.repositories.EstadoRepository;
import com.lucas.cursomc.repositories.ItemPedidoRepository;
import com.lucas.cursomc.repositories.PagamentoRepository;
import com.lucas.cursomc.repositories.PedidoRepository;
import com.lucas.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
